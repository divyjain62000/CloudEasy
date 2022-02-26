import { REQUEST_TYPE } from "../../constants/request-options"
import { BASE_URL } from "../../constants/app.constants";
import {URL_MAP} from '../../constants/url-mapper';
import { getToken } from "../auth/auth.service";

export const dashboardDataRequest = (userId) => {

    return new Promise((resolve, reject) => {
        const requestOption = {
            method: REQUEST_TYPE.POST,
            headers: { 'Authorization': getToken()},
        };
        fetch("/api/dashboard/"+userId, requestOption).then((response) => {
            if (response.ok) {
                return response.json();
            }
            if (response.status === 401) {
                window.location.href=BASE_URL+URL_MAP.LOGIN;
            }
            throw response;
        })
            .then((responseObj) => {
                if(responseObj.successful===true) {
                    resolve(responseObj.result);
                }else if(responseObj.exception===true) {
                    reject(responseObj.result);
                } 
            })
            .catch(error => {
                console.error("Error while trying to fetch dashboard data: " + error);
            })
    })

}