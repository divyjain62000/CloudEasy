import React from 'react';
import DialogTitle from '@mui/material/DialogTitle';
import Dialog from '@mui/material/Dialog';
import { DialogActions, DialogContent } from '@mui/material';
import { Button } from '@mui/material';

export const ConfirmationDialogComponent = (props) => {

  const accept=()=>{
    props.acceptAction();
  }

  const decline=()=>{
    props.declineAction(false);
  }

  return (
    <Dialog
      {...props}
      PaperProps={{
        style: {
          width: "500px",
          height: "150px",
          border: "4px solid skyblue"
        },
      }}
    >

      <DialogTitle style={{textAlign: "center"}}>
        {props.message}<br />
      </DialogTitle>
      <DialogActions style={{ justifyContent: "center"}}>
            <Button onClick={accept} variant='contained' color="success">Yes</Button>
            <Button onClick={decline} variant='contained' color="error">No</Button>
      </DialogActions>
    </Dialog>
  )
}