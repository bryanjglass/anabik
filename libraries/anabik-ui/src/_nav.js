export default {
    items: [
        {
            name: 'Dashboard',
            url: '/dashboard',
            icon: 'icon-speedometer',
            badge: {
                variant: 'primary',
                text: 'NEW'
            }
        },
        {
            title: true,
            name: 'Discover',
            class: '',
            wrapper: {
                element: '',
                attributes: {}
            }
        },
        {
            name: 'Search',
            url: '/theme/colors',
            icon: 'icon-magnifier'
        },
        {
            name: 'Explore',
            url: '/theme/typography',
            icon: 'icon-bulb'
        },
        {
            name: 'Data Types',
            url: '/types',
            icon: 'icon-notebook',
            children: [{
                name: 'Test Type'
            }]
        },
        {
            title: true,
            name: 'Visualize',
            class: '',
            wrapper: {
                element: '',
                attributes: {}
            }
        },

        {
            name: 'Charts',
            url: '/charts',
            icon: 'icon-pie-chart'
        },
        {
            name: 'Maps',
            url: '/maps',
            icon: 'icon-globe',
            children: [

            ]
        },
        {
            name: 'Graphs',
            url: '/graphs',
            icon: 'icon-link'
        },
        {
            divider: true
        },
        {
            title: true,
            name: 'Monitor'
        },
        {
            name: 'Health',
            url: '/health',
            icon: 'icon-heart',
            children: [
                {
                    name: 'Ingest',
                    url: '/health/ingest',
                    icon: 'icon-heart'
                },
                {
                    name: 'Services',
                    url: '/health/services',
                    icon: 'icon-heart'
                }
            ]
        },
        {
            name: 'Alerts',
            url: '/notifications',
            icon: 'icon-bell',
            children: [
                {
                    name: 'Alerts',
                    url: '/notifications/alerts',
                    icon: 'icon-bell'
                },
                {
                    name: 'Badges',
                    url: '/notifications/badges',
                    icon: 'icon-bell'
                },
                {
                    name: 'Modals',
                    url: '/notifications/modals',
                    icon: 'icon-bell'
                }
            ]
        },
        {
            name: 'Settings',
            url: 'http://coreui.io/vue/',
            icon: 'icon-wrench',
            class: 'mt-auto',
            variant: 'info'
        }
    ]
}
