import { REQUEST_TYPE, CONTENT_TYPE } from "../../../constants/request-options";
import { getToken } from "../../auth/auth.service";
import { BASE_URL } from "../../../constants/app.constants";
import { URL_MAP } from "../../../constants/url-mapper";


export const installDatabaseRequest = (instanceConfigurationRequest) => {

    return new Promise((resolve, reject) => {
        const requestOption = {
            method: REQUEST_TYPE.POST,
            headers: { 'Authorization': getToken(),'Content-Type': CONTENT_TYPE.APPLICATION_JSON },
            body: JSON.stringify(instanceConfigurationRequest)
        };
        fetch("/api/configure-instance/install-databases", requestOption).then((response) => {
            if (response.ok) return response.json();
            if (response.status === 401) {
                window.location.href=BASE_URL+URL_MAP.LOGIN;
            }
            throw response;
        })
            .then((responseObj) => {
                if(responseObj.successful===true) {
                    resolve(responseObj);
                }
                if(responseObj.exception===true) {
                    reject(responseObj.result);
                }
            })
            .catch(error => {
                console.error("Error while trying to install database: " + error);
            })
    })

}

export const installCompilerInterpreterRequest = (instanceConfigurationRequest) => {

    return new Promise((resolve, reject) => {
        const requestOption = {
            method: REQUEST_TYPE.POST,
            headers: { 'Authorization': getToken(),'Content-Type': CONTENT_TYPE.APPLICATION_JSON },
            body: JSON.stringify(instanceConfigurationRequest)
        };
        fetch("/api/configure-instance/install-compiler-interpreter", requestOption).then((response) => {
            if (response.ok) return response.json();
            if (response.status === 401) {
                window.location.href=BASE_URL+URL_MAP.LOGIN;
            }
            throw response;
        })
            .then((responseObj) => {
                resolve(responseObj);
            })
            .catch(error => {
                console.error("Error while trying to install database: " + error);
            })
    })

}


export const configureInstanceRequest = (instanceConfigurationRequest) => {

    return new Promise((resolve, reject) => {
        const requestOption = {
            method: REQUEST_TYPE.POST,
            headers: { 'Authorization': getToken(),'Content-Type': CONTENT_TYPE.APPLICATION_JSON },
            body: JSON.stringify(instanceConfigurationRequest)
        };
        fetch("/api/configure-instance/before-upload", requestOption).then((response) => {
            if (response.ok) return response.json();
            if (response.status === 401) {
                window.location.href=BASE_URL+URL_MAP.LOGIN;
            }
            throw response;
        })
            .then((responseObj) => {
                resolve(responseObj.message);
            })
            .catch(error => {
                console.error("Error while trying to configure server: " + error);
            })
    })

}
