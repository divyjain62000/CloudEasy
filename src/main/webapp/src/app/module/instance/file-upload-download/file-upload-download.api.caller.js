import { REQUEST_TYPE } from "../../../constants/request-options";
import { getToken } from "../../auth/auth.service";
import { BASE_URL } from "../../../constants/app.constants";
import { URL_MAP } from "../../../constants/url-mapper";


export const uploadFileRequest = (data) => {
    var promise = new Promise((resolve, reject) => {
        const requestOption = {
            method: REQUEST_TYPE.POST,
            headers: { 'Authorization': getToken() },
            body: data
        };
        fetch("/api/instance/upload-file", requestOption).then((response) => {
            if (response.ok) return response.json();
            if (response.status === 401) {
                window.location.href = BASE_URL + URL_MAP.LOGIN;
            }
            throw response;
        })
            .then((response) => {
                if (response.successful === true) {
                    resolve(response.result);
                }
                else if (response.exception === true) {
                    reject(response.result);
                }
            })
            .catch((error) => {
                console.log("Error while uploading file " + error);
            })
    });
    return promise;
}

export const uploadStaticWebsiteRequest = (data) => {
    var promise = new Promise((resolve, reject) => {
        const requestOption = {
            method: REQUEST_TYPE.POST,
            headers: { 'Authorization': getToken() },
            body: data
        };
        fetch("/api/instance/upload/static-website", requestOption).then((response) => {
            if (response.ok) return response.json();
            if (response.status === 401) {
                window.location.href = BASE_URL + URL_MAP.LOGIN;
            }
            throw response;
        })
            .then((response) => {
                if (response.successful === true) {
                    resolve(response.result);
                }
                else if (response.exception === true) {
                    reject(response.result);
                }
            })
            .catch((error) => {
                console.log("Error while uploading file " + error);
            })
    });
    return promise;
}



export const downloadFileRequest = (filePath, downloadInstanceId) => {
    var promise = new Promise((resolve, reject) => {
    const requestOption = {
        method: REQUEST_TYPE.GET,
        headers: { 'Authorization': getToken() },
    };
    fetch("/api/instance/download?filePath=" + filePath + "&instanceId=" + downloadInstanceId, requestOption).then((response) => {
        return response.blob();
    })
        .then(response => {
            const url = window.URL.createObjectURL(new Blob([response]),);
            const link = document.createElement('a');
            link.href = url;
            link.setAttribute('download', filePath,);
            document.body.appendChild(link);

            // Start download
            link.click();
            resolve(true);
        })
    });
    return promise;

}
