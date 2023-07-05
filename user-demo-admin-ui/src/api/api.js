import axios from 'axios';

let base = 'http://localhost:8088/';
const qs = require('qs');

// export const requestLogin = params => { return axios.post(`${base}/login`, params).then(res => res.data); };
export const listUsers= params => { return axios.get(`${base}/admin/users`, { params: params }); };

export const removeUser = params => { return axios.delete(`${base}/admin/users/${params}`); };

export const getUserEmail = params => { return axios.get(`${base}/admin/users/welcome-mail`, { params: params }); };
