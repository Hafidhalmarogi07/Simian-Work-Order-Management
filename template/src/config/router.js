const Dashboard = () => import('@pages/Dashboard.vue')
const Error404 = () => import('@pages/Error404.vue')
const Error500 = () => import('@pages/Error500.vue')
const Login = () => import('@pages/Login.vue')
const Register = () => import('@pages/Register.vue')
const ForgotPassword = () => import('@pages/ForgotPassword.vue')
const RecoverPassword = () => import('@pages/RecoverPassword.vue')
const Main = () => import('@pages/Main.vue')
const Profile = () => import('@pages/Profile.vue')
const Navbar = () => import('@components/Navbar.vue')
const Sidebar = () => import('@components/Sidebar.vue')
const Header = () => import('@components/Header.vue')
const Footer = () => import('@components/Footer.vue')

const mainPages = [
    {
        path: '/',
        name: 'dashboard',
        component: Dashboard,
    }, {
        path: '/error404',
        name: 'error404',
        component: Error404
    }, {
        path: '/error500',
        name: 'error500',
        component: Error500
    }, {
        path: '/users/me',
        name: 'profile',
        component: Profile,
        meta: {
            title: "Profile",
            subtitle: "my profile"
        }
    }
]

export default {
    routes: [
        {
            path: '',
            component: {
                template: '<router-view></router-view>'
            },
            children: [
                {
                    path: '',
                    component: Main,
                    children: [
                        {
                            path: '',
                            components: {
                                default: {
                                    template: '<router-view></router-view>'
                                },
                                sidebar: Sidebar,
                                navbar: Navbar,
                                header: Header,
                                footer: Footer
                            },
                            children: mainPages
                        }
                    ]
                }, {
                    path: '/login',
                    name: 'login',
                    component: Login
                }, {
                    path: '/register',
                    name: 'register',
                    component: Register
                }, {
                    path: '/forgot-password',
                    name: 'forgot_password',
                    component: ForgotPassword
                }, {
                    path: '/recover-password',
                    name: 'recover_password',
                    component: RecoverPassword
                }
            ]
        }
    ]
}
