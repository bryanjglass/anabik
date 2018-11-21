<template>
    <div class="animated fadeIn">
    <b-card>
        <b-input-group size="lg">
            <b-input-group-prepend>
                <b-dropdown :text="type" >
                    <b-dropdown-item v-for="dataType in dataTypes"
                                     :key="dataType.name"
                                     @click.prevent="type = dataType.name">
                        {{dataType.name}}
                    </b-dropdown-item>
                </b-dropdown>
            </b-input-group-prepend>
            <b-form-input v-model="query" @keypress.enter.native="performQuery"></b-form-input>
            <b-input-group-append>
                <b-btn variant="success" @click="performQuery">Search</b-btn>
            </b-input-group-append>
        </b-input-group>
    </b-card>
    <b-card>
        <b-table :items="items"
                 :striped="true"
                 :small="true"
                 :hover="true"
                 show-empty="true"></b-table>
    </b-card>
    </div>
</template>

<script>
    import axios from "axios";

    export default {
        name: "Search",
        data() {
            return {
                type: 'Select Type',
                query: '*',
                items: [],
                dataTypes: []
            };
        },
        created() {
            this.fetchDataTypes()
        },
        methods: {
            fetchDataTypes() {
                axios.get('/api/data-type')
                    .then((response) => {
                        this.dataTypes = response.data;
                    })
            },
            performQuery() {
                axios.get(`/api/query/${this.type}`, {
                    params: {
                        query: this.query
                    }
                })
                .then((response) => {
                    this.items = response.data;
                });
            }
        }
    }
</script>

<style scoped>

</style>
