import axios from 'axios';

let base = 'http://localhost:8099/';
const qs = require('qs');

// export const requestLogin = params => { return axios.post(`${base}/login`, params).then(res => res.data); };
export const requestLogin = params => { return axios.post(`${base}/user/password/auth`, qs.stringify(params), {headers: {'Content-Type': 'application/x-www-form-urlencoded'}}); };
export const requestRegister = params => { return axios.post(`${base}/user/register`, qs.stringify(params), {headers: {'Content-Type': 'application/x-www-form-urlencoded'}}); };
export const checkEmail = params => { return axios.get(`${base}/user/register/check`, { params: params }); };

export const getUserModel = params => { return axios.get(`${base}/user/model`, { params: params }); };
export const updateUserModel = params => { return axios.post(`${base}/user/model`, qs.stringify(params), {headers: {'Content-Type': 'application/x-www-form-urlencoded'}}); };
export const updatePassword = params => { return axios.post(`${base}/user/password`, qs.stringify(params), {headers: {'Content-Type': 'application/x-www-form-urlencoded'}}); };
