import {BASE_URL} from '../../constants/app.constants';
import { URL_MAP } from '../../constants/url-mapper';

export const logout=()=>{
    sessionStorage.removeItem("token");
    localStorage.removeItem("token");
    window.location.href=BASE_URL+URL_MAP.LOGIN;
}