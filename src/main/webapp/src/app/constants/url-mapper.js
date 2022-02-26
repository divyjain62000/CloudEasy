import { DashboardComponent } from '../module/dashboard/dashboard';
import { ConfigureInstanceComponent } from '../module/instance/configure/configure-instance.component';
import { CreateInstanceComponent } from '../module/instance/create-instance.component';
import { InstanceListComponent } from '../module/instance/instance-list.component';
import { LoginComponent } from '../module/login/login.component';
import { RegistrationComponent } from '../module/registration/registration.component';
import { FileUploadAndDownloadComponent } from '../module/instance/file-upload-download/file-upload-download.component';
import { HomeComponent } from '../module/home/home.component';
import { UploadStaticWebsite } from '../module/instance/file-upload-download/upload-static-website-component';

export const URL_COMPONENT_MAPPER_LIST = [
    { path: "/", component: <HomeComponent/> },
    { path: "/dashboard", component: <DashboardComponent /> },
    { path: "/login", component: <LoginComponent /> },
    { path: "/register", component: <RegistrationComponent /> },
    { path: "/servers", component: <InstanceListComponent /> },
    { path: "/launch-server", component: <CreateInstanceComponent /> },
    { path: "/configure-server", component: <ConfigureInstanceComponent /> },
    { path: "/file-access", component: <FileUploadAndDownloadComponent /> },
    { path: "/host-static-website", component: <UploadStaticWebsite /> },

];

export const URL_MAP = {
    DASHBOARD: "/dashboard",
    LOGIN: "/login",
    REGISTER: "/register",
    SERVERS: "/servers",
    LAUNCH_SERVER: "/launch-server",
    CONFIGURE_SERVER: "/configure-server",
    FILE_ACCESS: "/file-access",
    HOST_STATIC_WEBSITE: "/host-static-website",
    HOME: "/"
};

