import { userErr } from "../../constants/error/user.error";
import { CONTENT_TYPE, REQUEST_TYPE } from "../../constants/request-options"

export const registrationRequest = (user) => {

    return new Promise((resolve, reject) => {
        const requestOption = {
            method: REQUEST_TYPE.POST,
            headers: { 'Content-Type': CONTENT_TYPE.APPLICATION_JSON },
            body: JSON.stringify(user)
        };
        fetch("/register", requestOption).then((response) => {
            if (response.ok) return response.json();
            if (response.status === 401) {
                reject(userErr.INVALID_CREDENTIALS);
            }
            throw response;
        })
            .then((responseObj) => {
                if(responseObj.successful===true) resolve(responseObj.message);
                else if(responseObj.exception===true) reject(responseObj.result);
            })
            .catch(error => {
                console.error("Error while trying to registering user: " + error);
            })
    })

}