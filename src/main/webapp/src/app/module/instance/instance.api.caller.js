import { BASE_URL } from "../../constants/app.constants";
import { REQUEST_TYPE, CONTENT_TYPE } from "../../constants/request-options"
import {URL_MAP} from '../../constants/url-mapper';
import { NewlineText } from "../../shared/components/container.component";
import { getToken } from "../auth/auth.service";




export const createInstanceRequest = (instanceRequest) => {

    return new Promise((resolve, reject) => {
        const requestOption = {
            method: REQUEST_TYPE.POST,
            headers: { 'Authorization': getToken(),'Content-Type': CONTENT_TYPE.APPLICATION_JSON },
            body: JSON.stringify(instanceRequest)
        };
        fetch("/api/instance/create", requestOption).then((response) => {
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
                console.error("Error while trying to create instance: " + error);
            })
    })

}



export const getAllInstancesOfUser = (id) => {

    return new Promise((resolve, reject) => {
        const requestOption = {
            method: REQUEST_TYPE.POST,
            headers: { 'Authorization': getToken()},
        };
        fetch("/api/instance/"+id, requestOption).then((response) => {
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
                    let instances=responseObj.result;
                    let instanceList=[];
                    for(let i=0;i<instances.length;i++) {
                        let data=createData(instances[i].instanceId,instances[i].status,instances[i]);
                        instanceList.push(data);
                      }
                    resolve(instanceList);
                }else if(responseObj.exception===true) {
                    reject(responseObj.result);
                } 
            })
            .catch(error => {
                console.error("Error while trying to get all instances of user: " + error);
            })
    })

}


export const startInstanceById=(id)=>{
    return new Promise((resolve, reject) => {
        const requestOption = {
            method: REQUEST_TYPE.POST,
            headers: { 'Authorization': getToken()},
        };
        fetch("/api/instance/start/"+id, requestOption).then((response) => {
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
                    resolve(responseObj.message);
                }else if(responseObj.exception===true) {
                    reject(responseObj.result);
                } 
            })
            .catch(error => {
                console.error("Error while trying to start instance: " + error);
            })
    })
}


export const stopInstanceById=(id)=>{
    return new Promise((resolve, reject) => {
        const requestOption = {
            method: REQUEST_TYPE.POST,
            headers: { 'Authorization': getToken()},
        };
        fetch("/api/instance/stop/"+id, requestOption).then((response) => {
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
                    resolve(responseObj.message);
                }else if(responseObj.exception===true) {
                    reject(responseObj.result);
                } 
            })
            .catch(error => {
                console.error("Error while trying to stop instance: " + error);
            })
    })
}


export const rebootInstanceById=(id)=>{
    return new Promise((resolve, reject) => {
        const requestOption = {
            method: REQUEST_TYPE.POST,
            headers: { 'Authorization': getToken()},
        };
        fetch("/api/instance/reboot/"+id, requestOption).then((response) => {
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
                    resolve(responseObj.message);
                }else if(responseObj.exception===true) {
                    reject(responseObj.result);
                } 
            })
            .catch(error => {
                console.error("Error while trying to stop instance: " + error);
            })
    })
}

export const terminateInstanceById=(id)=>{
    return new Promise((resolve, reject) => {
        const requestOption = {
            method: REQUEST_TYPE.POST,
            headers: { 'Authorization': getToken()},
        };
        fetch("/api/instance/terminate/"+id, requestOption).then((response) => {
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
                    resolve(responseObj.message);
                }else if(responseObj.exception===true) {
                    reject(responseObj.result);
                } 
            })
            .catch(error => {
                console.error("Error while trying to stop instance: " + error);
            })
    })
}





function createData(col1, col2,details) {
    let instanceDetails=[]
    let softwaresInstalled="\n";
    // eslint-disable-next-line array-callback-return
    details.softwaresInstalled.map((swi,index)=>{
        softwaresInstalled+=swi+"\n"
    });



      instanceDetails.push({key: "Server Id",value: details.instanceId,copyClipboard: true});
      instanceDetails.push({key: "Status",value: details.status,copyClipboard: false});
      instanceDetails.push({key: "Operating System",value: details.operatingSystem,copyClipboard: false});
      instanceDetails.push({key: "IPv4 Address",value: (details.ipv4Address===null || details.ipv4Address.length===0)?"-":details.ipv4Address,copyClipboard: true});
      instanceDetails.push({key: "Softwares Installed",value: softwaresInstalled.length===0?"-":<NewlineText text={softwaresInstalled} />,copyClipboard: false});
    let data={
      id:details.id,
      col1,
      col2,
      detailTitle: "Server Details",
      details: instanceDetails,
    };
    return data;
  }
