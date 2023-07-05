import NotFound from './views/404.vue'
import Home from './views/Home.vue'
import Main from './views/Main.vue'
import Table from './views/Table.vue'


let routes = [
    {
        path: '/404',
        component: NotFound,
        name: '',
        hidden: true
    },
    {
        path: '/',
        component: Home,
        name: '用户管理',
        iconCls: 'fa fa-user', //'el-icon-message',//图标样式class
        redirect: { path: '/users' },
        children: [
            { path: '/main', component: Main, name: '主页', hidden: true },
            { path: '/users', component: Table, name: '用户列表'},
        ]
    },
    {
        path: '*',
        hidden: true,
        redirect: { path: '/404' }
    }
];

export default routes;
