import React from 'react';
import DialogTitle from '@mui/material/DialogTitle';
import Dialog from '@mui/material/Dialog';
import TaskDoneIcon from '@mui/icons-material/CheckCircle';
import WarningIcon from '@mui/icons-material/Warning';
import { DialogActions } from '@mui/material';
import { Button } from '@mui/material';
import { severity } from '../../constants/app.constants';

export const DialogWrapperComponent = (props) => {

  const warningAccept=()=>{
    props.closeAction(true);
  }

  const warningDecline=()=>{
    props.closeAction(false);
  }

  return (
    <Dialog
      {...props}
      PaperProps={{
        style: {
          width: "500px",
          height: "auto"
        },
      }}
    >

      <DialogTitle style={{textAlign: "center",paddingTop: "30px"}}>
        {props.severity===severity.SUCCESS && <TaskDoneIcon color="success"></TaskDoneIcon>}
        {props.severity===severity.INFO && <TaskDoneIcon color="info"></TaskDoneIcon>}
        {props.severity===severity.ERROR && <TaskDoneIcon color="error"></TaskDoneIcon>}
        {props.severity===severity.WARNING && <WarningIcon color="warning"></WarningIcon>}
        &nbsp;&nbsp;&nbsp;{props.message}<br />
      </DialogTitle>
      <DialogActions style={{ justifyContent: "center"}}>
        {props.severity === severity.SUCCESS && <Button onClick={props.closeAction} variant='contained' color="success">Ok</Button>}
        {props.severity === severity.INFO && <Button onClick={props.closeAction} variant='contained' color="info">Ok</Button>}
        {props.severity === severity.ERROR && <Button onClick={props.closeAction} variant='contained' color="error">Ok</Button>}
        {
          props.severity === severity.WARNING &&
          <div>
            <Button onClick={warningAccept} variant='contained' color="error">Yes</Button>&nbsp;&nbsp;
            <Button onClick={warningDecline} variant='contained' color="success">No</Button>
          </div>
        }
      </DialogActions>
    </Dialog>
  )
}