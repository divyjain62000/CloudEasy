import { userErr } from "../../constants/error/user.error";
import { CONTENT_TYPE, REQUEST_TYPE } from "../../constants/request-options"

export const loginRequest = (credentials) => {

    return new Promise((resolve, reject) => {
        const requestOption = {
            method: REQUEST_TYPE.POST,
            headers: { 'Content-Type': CONTENT_TYPE.APPLICATION_JSON },
            body: JSON.stringify(credentials)
        };
        fetch("/authenticate", requestOption).then((response) => {
            if (response.ok) return response.json();
            if (response.status === 401) {
                reject(userErr.INVALID_CREDENTIALS);
            }
            throw response;
        })
            .then((responseObj) => {
                resolve(responseObj);
            })
            .catch(error => {
                console.error("Error while trying to authenticate user: " + error);
            })
    })

}