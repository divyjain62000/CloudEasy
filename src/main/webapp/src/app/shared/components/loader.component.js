import React from 'react';
import DialogTitle from '@mui/material/DialogTitle';
import Dialog from '@mui/material/Dialog';
import Box from '@mui/material/Box';
import LinearProgress from '@mui/material/LinearProgress';


export const LoaderComponent = (props) => {

    return (
        <Dialog
            {...props}
            PaperProps={{
                style: {
                  width: "600px",
                  height: "100px"
                },
              }}
        >

            <DialogTitle >
              {props.message}
            </DialogTitle>
            <Box style={{padding: "5px"}} sx={{ width: '100%' }}>
            <LinearProgress />
              </Box>
        </Dialog>
    )
}