/* eslint-disable react-hooks/exhaustive-deps */
import React from 'react';
import PropTypes from 'prop-types';
import Box from '@mui/material/Box';
import Collapse from '@mui/material/Collapse';
import IconButton from '@mui/material/IconButton';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Typography from '@mui/material/Typography';
import Paper from '@mui/material/Paper';
import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown';
import KeyboardArrowUpIcon from '@mui/icons-material/KeyboardArrowUp';
import { ContainerItem, WrapperContainer } from '../../shared/components/container.component';
import StartIcon from '@mui/icons-material/PlayArrow';
import StopIcon from '@mui/icons-material/Square';
import ReloadIcon from '@mui/icons-material/ReplayOutlined';
import TerminateIcon from '@mui/icons-material/Clear';
import CopyClipboardIcon from '@mui/icons-material/CopyAllSharp';
import Tooltip from '@mui/material/Tooltip';
import { AppbarDrawerComponent } from '../../shared/components/appbar-drawer.component';
import { getUserId, isUserActive } from '../auth/auth.service';
import { BASE_URL, severity } from '../../constants/app.constants';
import { URL_MAP } from '../../constants/url-mapper';
import { getAllInstancesOfUser, stopInstanceById, startInstanceById, rebootInstanceById, terminateInstanceById } from './instance.api.caller';
import { LoaderComponent } from '../../shared/components/loader.component';
import { DialogWrapperComponent } from '../../shared/components/dialog.component';
import { Button } from '@mui/material';
import AddIcon from '@mui/icons-material/AddToQueue';
import { configureInstanceRequest } from './configure/configure-instance.api.caller';



export const InstanceListComponent = () => {

  const [showComponents, setShowComponents] = React.useState(false);
  const [rows, setRows] = React.useState([]);


  React.useEffect(() => {
    if (isUserActive() === false) {
      window.location.href = BASE_URL + URL_MAP.LOGIN;
    } else {

      getAllInstancesOfUser(getUserId()).then((instanceList) => {
        setRows(instanceList);
      }, (exception) => {
        //currently there is not seen of custom exception occur
      })
        .catch(error => {
          console.error("Error while trying to get all instances of user: " + error);
        });
      setShowComponents(true);
    }
  }, []);
  return (
    <div>
      {showComponents && <AppbarDrawerComponent activeComponent activeLink={URL_MAP.SERVERS} element={<ListComponent rows={rows} />}></AppbarDrawerComponent>}
    </div>
  )
}




const ListComponent = (props) => {

  const rows = props.rows;

  const header = ["Server ID", "Status", "Action",""];


  return (
    <div>
      <WrapperContainer boxShadow={false}>
        <ContainerItem xs={6} sm={6} md={6} lg={6} xl={6} style={{ marginBottom: "2%", paddingLeft: "1%" }}>
          <Typography variant="h4">Servers</Typography>
        </ContainerItem>
        <ContainerItem xs={6} sm={6} md={6} lg={6} xl={6} style={{ marginBottom: "2%", textAlign: "right", paddingRight: "2%" }}>
          <Button 
          color="info" 
          variant="outlined" 
          style={{ fontSize: "16px", textTransform: "capitalize" }}
          onClick={()=>{
            window.location.href=BASE_URL+URL_MAP.LAUNCH_SERVER;
          }}
          >
            <AddIcon style={{ fontSize: "20px" }} />&nbsp;Launch Server
          </Button>
        </ContainerItem>
        <ContainerItem xs={12} sm={12} md={12} lg={12} xl={12}>
          <CollapsibleTable rows={rows} header={header}></CollapsibleTable>
        </ContainerItem>

      </WrapperContainer>
    </div>
  )
}


const Row = (props) => {
  const { row } = props;
  const [open, setOpen] = React.useState(false);

  const [progressLoader, setProgressLoader] = React.useState(false);
  const [progressMessage, setProgressMessage] = React.useState("");
  const [dialogOpen, setDialogOpen] = React.useState(false);
  const [dialogMessage, setDialogMessage] = React.useState("");
  const [variant, setVariant] = React.useState("");
  const [warningDialog, setWarningDialog] = React.useState(false);
  const [instanceId,setInstanceId]=React.useState(null);


  const startInstance = async (id) => {
    setProgressLoader(true);
    setProgressMessage("Please wait while starting your server.");
    await startInstanceById(id).then((message) => {
      window.location.reload(false);
      setProgressLoader(false);
      setProgressMessage("");
      setVariant(severity.SUCCESS);
      window.location.reload(false);
    }, (exception) => {
      //currently there is not seen of custom exception occur
    })
      .catch(error => {
        console.error("Error while trying to start server: " + error);
      });
  }


  const stopInstance = async (id) => {
    setProgressLoader(true);
    setProgressMessage("Please wait while stopping your server.");
    await stopInstanceById(id).then((message) => {
      setProgressLoader(false);
      setProgressMessage("");
      setVariant(severity.SUCCESS);
      window.location.reload(false);
    }, (exception) => {
      //currently there is not seen of custom exception occur
    })
      .catch(error => {
        console.error("Error while trying to stop server: " + error);
      });
  }


  const rebootInstance = async (id) => {
    setProgressLoader(true);
    setProgressMessage("Please wait while rebooting your server.");
    await rebootInstanceById(id).then((message) => {
      setProgressLoader(false);
      setProgressMessage("");
      setDialogOpen(true);
      setDialogMessage(message);
      setVariant(severity.SUCCESS);
    }, (exception) => {
      //currently there is not seen of custom exception occur
    })
      .catch(error => {
        console.error("Error while trying to reboot server: " + error);
      });
  }

  const terminateInstanceHelper = async () => {
    setProgressLoader(true);
    setProgressMessage("Please wait while terminating your server");
    await terminateInstanceById(instanceId).then((message) => {
      setProgressLoader(false);
      setProgressMessage("");
      window.location.reload(false);
    }, (exception) => {
      //currently there is not seen of custom exception occur
    })
      .catch(error => {
        console.error("Error while trying to reboot server: " + error);
      });
  }

  const terminateInstance=async (id)=>{
    setWarningDialog(true);
    setDialogMessage("If you terminate server, then all data associated with it will be lost. Are you sure you want to terminate server?");
    setVariant(severity.WARNING);
    setInstanceId(id);
  }




  const autoConfigInstance = async (id) => {
    setProgressLoader(true);
    setProgressMessage("Please wait while configuring your server.");
    const instanceConfiguration = {
      database: null,
      compilerInterpreter: null,
      userId: getUserId(),
      ec2InstanceId: id
  };
    await configureInstanceRequest(instanceConfiguration).then((message) => {
      setProgressLoader(false);
      setProgressMessage("");
      setDialogOpen(true);
      setDialogMessage(message);
      setVariant(severity.SUCCESS);
    }, (exception) => {
      //currently there is not seen of custom exception occur
    })
      .catch(error => {
        console.error("Error while trying to start server: " + error);
      });
  }



  return (
    <React.Fragment>
      <LoaderComponent open={progressLoader} message={progressMessage} />
      <DialogWrapperComponent open={dialogOpen} message={dialogMessage} severity={variant} closeAction={() => {
        setDialogOpen(false);
        setDialogMessage("");
        window.location.reload(false);
      }}
      />
      <DialogWrapperComponent open={warningDialog} message={dialogMessage} severity={variant} closeAction={(action) => {
        setWarningDialog(false)
        if(action===true) terminateInstanceHelper();
      }}
      />

      <TableRow sx={{ '& > *': { borderBottom: 'unset' } }}>
        <TableCell>
          <IconButton
            aria-label="expand row"
            size="small"
            onClick={() => setOpen(!open)}
          >
            {open ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}
          </IconButton>
        </TableCell>
        <TableCell component="th" scope="row">
          <CopyClipboardIcon style={{ color: "grey", cursor: "pointer" }} onClick={() => navigator.clipboard.writeText(row.col1)}></CopyClipboardIcon> {row.col1}
        </TableCell>
        <TableCell style={{ color: row.col2 === "Running" ? "green" : "red" }} align="left">{row.col2}</TableCell>
        <TableCell align="left">
          <Tooltip title="Start">
            {
              row.col2 !== "Running" ?
                <StartIcon onClick={() => { startInstance(row.id); }} style={{ width: "30px", cursor: "pointer" }} color="success" /> :
                <StartIcon style={{ width: "30px", cursor: "not-allowed", color: "grey" }} />
            }
          </Tooltip>
          <Tooltip title="Stop">
            {row.col2 !== "Stopped" ? <StopIcon onClick={() => { stopInstance(row.id); }} color="error" style={{ marginLeft: "10px", width: "18px", cursor: "pointer" }} /> : <StopIcon style={{ marginLeft: "10px", width: "18px", cursor: "not-allowed", color: "grey" }} />}
          </Tooltip>
          <Tooltip title="Reboot">
            <ReloadIcon onClick={() => { rebootInstance(row.id); }} color="warning" style={{ marginLeft: "10px", width: "20px", cursor: "pointer" }} />
          </Tooltip>
          <Tooltip title="Terminate">
            <TerminateIcon onClick={() => { terminateInstance(row.id); }} style={{ marginLeft: "10px", width: "20px", cursor: "pointer" }} />
          </Tooltip>
        </TableCell>
        <TableCell component="th" scope="row">
          <Button onClick={() => { autoConfigInstance(row.id); }} variant="contained">Auto Config</Button>
        </TableCell>
      </TableRow>
      <TableRow>
        <TableCell style={{ paddingBottom: 0, paddingTop: 0 }} colSpan={6}>
          <Collapse in={open} timeout="auto" unmountOnExit>
            <Box sx={{ margin: 1 }}>
              <Typography variant="h6" gutterBottom component="div">
                {row.detailTitle}
              </Typography>
              <Table size="small" aria-label="purchases">
                <TableBody>
                  <TableRow>
                    <WrapperContainer boxShadow={false}>
                      {
                        row.details.map((detailRow, index) => {

                          return (
                            <ContainerItem xs={12} sm={12} md={6} lg={4} xl={4}>
                              <TableCell style={{ border: "none" }} component="th" scope="row" key={detailRow.key}>
                                <b>{detailRow.key}:</b> <span>{detailRow.value}  {detailRow.copyClipboard === true && <CopyClipboardIcon style={{ color: "grey", cursor: "pointer" }} onClick={() => navigator.clipboard.writeText(detailRow.value)}></CopyClipboardIcon>}</span>
                              </TableCell>
                            </ContainerItem>
                          )
                        })
                      }
                    </WrapperContainer>
                  </TableRow>
                </TableBody>
              </Table>
            </Box>
          </Collapse>
        </TableCell>
      </TableRow>
    </React.Fragment>
  );
}

Row.propTypes = {
  row: PropTypes.shape({
    calories: PropTypes.number.isRequired,
    carbs: PropTypes.number.isRequired,
    fat: PropTypes.number.isRequired,
    history: PropTypes.arrayOf(
      PropTypes.shape({
        amount: PropTypes.number.isRequired,
        customerId: PropTypes.string.isRequired,
        date: PropTypes.string.isRequired,
      }),
    ).isRequired,
    name: PropTypes.string.isRequired,
    price: PropTypes.number.isRequired,
    protein: PropTypes.number.isRequired,
  }).isRequired,
};

const CollapsibleTable = (props) => {
  return (
    <TableContainer component={Paper}>
      <Table aria-label="collapsible table">
        <TableHead>
          <TableRow style={{ background: "#DDD" }}>
            <TableCell />
            {props.header.map((title) => (
              <TableCell align="left" style={{ fontWeight: "bold" }}>{title}</TableCell>
            ))}
          </TableRow>
        </TableHead>
        <TableBody>
          {props.rows.map((row, index) => (
            <Row key={row.col1} row={row} index={index} />
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
}