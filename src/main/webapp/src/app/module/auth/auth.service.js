import jwt_decode from 'jwt-decode';

export const getUserId=()=>{
    const token=localStorage.getItem("token");
    const user=jwt_decode(token);
    return user.user.id;
}


export const getUserName=()=>{
    const token=localStorage.getItem("token");
    const object=jwt_decode(token);
    const user=object.user;
    return user.firstName+" "+user.lastName;
}


export const isUserActive=()=>{
    const c=localStorage.getItem("token");
    if(c===null) return false;
    return true;
}

export const getToken=()=>{
    if(isUserActive()===false) return "";
    const token="Bearer "+localStorage.getItem("token");
    return token;
}