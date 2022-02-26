import React from 'react';
import DialogTitle from '@mui/material/DialogTitle';
import Dialog from '@mui/material/Dialog';
import { DialogActions } from '@mui/material';
import { Button } from '@mui/material';
import { OutlinedTextFieldWrapper } from '../../../shared/components/container.component';
import { getUserId } from '../../auth/auth.service';
import { installDatabaseRequest,installCompilerInterpreterRequest } from './configure-instance.api.caller';
import { severity } from '../../../constants/app.constants';
import { LoaderComponent } from '../../../shared/components/loader.component';
import { DialogWrapperComponent } from '../../../shared/components/dialog.component';
import { extractInstanceId } from '../instance-helper';


export const ConfigureInstanceDialogComponent = (props) => {

    const [instanceId, setInstanceId] = React.useState(null);
    const [progressLoader, setProgressLoader] = React.useState(false);
    const [progressMessage, setProgressMessage] = React.useState("");
    const [dialogOpen, setDialogOpen] = React.useState(false);
    const [dialogMessage, setDialogMessage] = React.useState("");
    const [variant, setVariant] = React.useState("");
    const [err,setErr]= React.useState(false);
    const [errMsg,setErrMsg]=React.useState("");

    const accept = async () => {

        const instanceConfiguration = {
            database: props.database,
            compilerInterpreter: props.compilerInterpreter,
            userId: getUserId(),
            ec2InstanceId: extractInstanceId(instanceId)
        };
        //alert(JSON.stringify(instanceConfiguration));
        setProgressLoader(true);
        setProgressMessage("Installing...");
        if (props.database != null) {
            props.closeAction();
            await installDatabaseRequest(instanceConfiguration).then((response) => {
                setProgressLoader(false);
                setProgressMessage("");
                setVariant(severity.SUCCESS);
                setDialogOpen(true);
                setDialogMessage("Installed Successfully!!");
            }, (exception) => {
                props.closeAction();
                props.openAction();
                setProgressLoader(false);
                setProgressMessage("");
                setErr(true);
                setErrMsg(exception.ID_ERR!=null?exception.ID_ERR:exception.INSTANCE_ERR!=null?exception.INSTANCE_ERR :"");
            })
                .catch(error => {

                });

        }
        else if (props.compilerInterpreter != null) {
            props.closeAction();
            await installCompilerInterpreterRequest(instanceConfiguration).then((response) => {
                setProgressLoader(false);
                setProgressMessage("");
                setVariant(severity.SUCCESS);
                setDialogOpen(true);
                setDialogMessage("Installed Successfully!!");
            }, (exception) => {
                props.closeAction();
                props.openAction();
                setProgressLoader(false);
                setProgressMessage("");
                setErr(true);
                setErrMsg(exception.ID_ERR!=null?exception.ID_ERR:exception.INSTANCE_ERR!=null?exception.INSTANCE_ERR :"");
            })
                .catch(error => {

                });
        }
    }

    const decline = () => {
        props.closeAction();
    }

    return (
        <div>
            <LoaderComponent open={progressLoader} message={progressMessage} />
            <DialogWrapperComponent open={dialogOpen} message={dialogMessage} severity={variant} closeAction={() => {
                setDialogOpen(false);
            }}
            />
            <Dialog
                {...props}
                PaperProps={{
                    style: {
                        width: "500px",
                        height: "auto",
                    },
                }}
            >

                <DialogTitle style={{ textAlign: "left" }}>
                    <OutlinedTextFieldWrapper
                        label="Server Id"
                        style={{ width: "100%" }}
                        onChange={(ev) => {
                            setInstanceId(ev.target.value);
                        }}
                        error={err}
                        helperText={errMsg}
                    />
                </DialogTitle>
                <DialogActions style={{ justifyContent: "right" }}>
                    <Button onClick={accept} style={{ align: "" }} variant='contained' color="primary">Install</Button>
                    <Button onClick={decline} variant='contained' color="error">Cancel</Button>
                </DialogActions>
            </Dialog>
        </div>
    )
}