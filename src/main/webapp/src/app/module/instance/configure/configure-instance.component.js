/* eslint-disable jsx-a11y/alt-text */
import React from 'react';
import { WrapperContainer, ContainerItem } from '../../../shared/components/container.component';
import { BASE_URL } from '../../../constants/app.constants';
import { URL_MAP } from '../../../constants/url-mapper';
import { AppbarDrawerComponent } from '../../../shared/components/appbar-drawer.component';
import { isUserActive, } from '../../auth/auth.service';
import { Typography } from '@mui/material';
import { Button } from '@mui/material';
import { databases } from '../../../constants/db/databases.constants';
import { compilerInterpreters } from '../../../constants/compiler-interpreter/compilter-interpreter.constants';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import { ConfigureInstanceDialogComponent } from './configure-instance-dialog.component';

export const ConfigureInstanceComponent = () => {

    const [showComponents, setShowComponents] = React.useState(false);


    React.useEffect(() => {
        if (isUserActive() === false) {
            window.location.href = BASE_URL + URL_MAP.LOGIN;
        } else {
            setShowComponents(true);
        }
    }, []);
    return (
        <div>
            {showComponents && <AppbarDrawerComponent activeComponent activeLink={URL_MAP.CONFIGURE_SERVER} element={<ListComponent />}></AppbarDrawerComponent>}
        </div>
    )
}




const ListComponent = (props) => {

    const [db,setDb]=React.useState(null);
    const [cmpInt,setCmpInt]=React.useState(null);
    const [dialogOpen,setDialogOpen]=React.useState(false);


    const installDatabase=(dbName)=>{
        setDb(dbName);
        setCmpInt(null);
        setDialogOpen(true);
    }

    const installCompilerAndInterpreter=(ci)=>{
        setCmpInt(ci);
        setDb(null);
        setDialogOpen(true);
    }


    return (
        <div>
            <ConfigureInstanceDialogComponent open={dialogOpen} database={db} compilerInterpreter={cmpInt} 
            closeAction={()=>{
                setDialogOpen(false);
            }} 
            openAction={()=>{
                setDialogOpen(true);
            }}
            />
            <WrapperContainer boxShadow={false}>
                <ContainerItem xs={12} sm={12} md={12} lg={12} xl={12} style={{ marginBottom: "2%", paddingLeft: "1%" }}>
                    <Typography variant="h4">Configure Server</Typography>
                </ContainerItem>
                <WrapperContainer boxShadow={false} style={{borderTop: '1px solid #DDD',borderBottom: '1px solid #DDD'}}>
                <ContainerItem xs={12} sm={12} md={12} lg={12} xl={12} style={{marginTop: "1%",marginBottom: "2%", paddingLeft: "2%" }}>
                    <Typography color="primary" variant="h5">Databases</Typography>
                </ContainerItem>
                {
                    databases.map((database) => {
                        return (
                            <ContainerItem lg={3} style={{ marginBottom: "2%",paddingLeft: "2%" }}>
                                <Card sx={{ minWidth: 265 }}>
                                    <CardContent style={{textAlign: "center"}}>
                                    <Typography variant="h6">{database.value}</Typography><br />
                                    <img src={database.img} width="50%" height="110px" />
                                    
                                    </CardContent>
                                    <CardContent style={{textAlign: "right"}}>
                                    <Typography variant="h6"><Button onClick={()=>{
                                        installDatabase(database.name);
                                    }} variant="contained">Install</Button></Typography>    
                                    </CardContent>
                                </Card>
                            </ContainerItem>
                        )
                    })
                }
                </WrapperContainer>
                <WrapperContainer boxShadow={false} style={{borderTop: '1px solid #DDD',borderBottom: '1px solid #DDD'}}>
                <ContainerItem xs={12} sm={12} md={12} lg={12} xl={12} style={{marginTop: "1%",marginBottom: "2%", paddingLeft: "2%" }}>
                    <Typography color="primary" variant="h5">Compilers &amp; Interpreters</Typography>
                </ContainerItem>
                {
                    compilerInterpreters.map((ci) => {
                        return (
                            <ContainerItem lg={3} style={{ marginBottom: "2%",paddingLeft: "2%" }}>
                                <Card sx={{ minWidth: 265 }}>
                                    <CardContent style={{textAlign: "center"}}>
                                    <Typography variant="h6">{ci.value}</Typography><br />
                                    <img src={ci.img} width="50%" height="110px" />
                                    
                                    </CardContent>
                                    <CardContent style={{textAlign: "right"}}>
                                    <Typography variant="h6"><Button onClick={()=>{
                                        installCompilerAndInterpreter(ci.name);
                                    }} variant="contained">Install</Button></Typography>    
                                    </CardContent>
                                </Card>
                            </ContainerItem>
                        )
                    })
                }
                </WrapperContainer>
            </WrapperContainer>
        </div>
    )
}
