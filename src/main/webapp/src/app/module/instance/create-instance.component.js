import React from 'react';
import { getUserId, isUserActive } from '../auth/auth.service';
import { BASE_URL, severity } from '../../constants/app.constants';
import { URL_MAP } from '../../constants/url-mapper';
import { AppbarDrawerComponent } from '../../shared/components/appbar-drawer.component';
import { getAllOperatingSystem } from '../operating-system/operating-system.api.caller';
import { WrapperContainer, ContainerItem } from '../../shared/components/container.component';
import { Typography,Button } from '@mui/material';
import { Divider } from '@mui/material';
import { ConfirmationDialogComponent } from '../../shared/components/confirmation-dialog.component';
import { DialogWrapperComponent } from '../../shared/components/dialog.component';
import { createInstanceRequest } from './instance.api.caller';
import { LoaderComponent } from '../../shared/components/loader.component';

export const CreateInstanceComponent = () => {

    const [showComponents, setShowComponents] = React.useState(false);
    const [operatingSystems, setOperatingSystems] = React.useState([]);

    // eslint-disable-next-line react-hooks/exhaustive-deps
    React.useEffect(async () => {
        if (isUserActive() === false) {
            window.location.href = BASE_URL + URL_MAP.LOGIN;
        } else {

            await getAllOperatingSystem().then((osList) => {
                setOperatingSystems(osList);
            }, (exception) => {
                //currently there is no scenario of custom exception occur
            })
                .catch(error => {
                    console.error("Error while trying to get all instances of user: " + error);
                });

            setShowComponents(true);
        }
    }, []);

    return (
        <div>
            {showComponents && <AppbarDrawerComponent activeComponent activeLink={URL_MAP.LAUNCH_SERVER} element={<OSListComponent data={operatingSystems} />}></AppbarDrawerComponent>}
        </div>
    )
}


const OSListComponent = (props) => {
    const osList = props.data;
    const [instanceRequest,setInstanceRequest]=React.useState({});
    const [confirmationDialogOpen,setConfirmationDialogOpen]=React.useState(false);
    const [dialogOpen,setDialogOpen]=React.useState(false);
    const [progressLoader, setProgressLoader] = React.useState(false);
  const [progressMessage, setProgressMessage] = React.useState("");

    const launchInstance=(osId,userId)=>{   
        
        const instanceRequestObj={
            userId: userId,
            operatingSystemId: osId
        };
        setInstanceRequest(instanceRequestObj);
        setConfirmationDialogOpen(true);
    }

    const launchInstanceHelper=async ()=>{
        setProgressLoader(true);
        setProgressMessage("Please wait for some time, launching your server.");
        await createInstanceRequest(instanceRequest).then((response)=>{
            setDialogOpen(true);
            setProgressLoader(false);
            setProgressMessage("");
        },(exception)=>{
            //currently there is no scenario of custom exception occur
        })
        .catch(error=>{
            console.error("Error while trying to create instance: " + error);
        });

    }

    return (
        <div>
            <LoaderComponent open={progressLoader} message={progressMessage} />
            <ConfirmationDialogComponent 
            open={confirmationDialogOpen} 
            message={"Are you sure you want to launch server?"}
            acceptAction={()=>{
                launchInstanceHelper();
                setConfirmationDialogOpen(false);
            }} 
            declineAction={(value)=>{
                setInstanceRequest({});
                setConfirmationDialogOpen(false);
            }}
            />
            <DialogWrapperComponent open={dialogOpen} closeAction={()=>{
                setDialogOpen(false);
                window.location.href=BASE_URL+URL_MAP.SERVERS;
            }} severity={severity.SUCCESS} message={"Server launched successfully!!"} />
        <WrapperContainer boxShadow={false}>
          <ContainerItem xs={12} sm={12} md={12} lg={12} xl={12} style={{ marginBottom: "2%", paddingLeft: "1%" }}>
            <Typography variant="h4">Launch Server</Typography>
          </ContainerItem>
          <Divider />
        {
          osList.map((os)=>{
            return(
            <ContainerItem xs={12} sm={12} md={12} lg={12} xl={12} style={{border: '1px solid #DDD',margin: '2px',padding: "15px"}}>
                <Typography variant="body1"><strong>{os.imageOf}</strong></Typography><br />
                <Typography variant='body2'>
                    <strong>Hard Disk Drive: </strong>{os.hdd} GB&nbsp;&nbsp;&nbsp;&nbsp;
                    <strong>RAM: </strong>{os.ram} GB&nbsp;&nbsp;&nbsp;&nbsp;
                </Typography>
                <Button variant="contained" onClick={()=>{
                    launchInstance(os.id,getUserId());
                }} style={{textTransform: "capitalize",float: "right"}}>Launch</Button>
            </ContainerItem>
            )
          })
        }
            
        </WrapperContainer>
      </div>
    )
}