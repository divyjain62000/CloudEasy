import React from 'react';
import { AppbarDrawerComponent } from "../../shared/components/appbar-drawer.component";
import { BASE_URL } from '../../constants/app.constants';
import { getUserId, isUserActive } from '../auth/auth.service';
import { URL_MAP } from '../../constants/url-mapper';
import { WrapperContainer, ContainerItem } from '../../shared/components/container.component';
import { Typography, Button } from '@mui/material';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import ServerIcon from '@mui/icons-material/Storage'
import AddIcon from '@mui/icons-material/AddToQueue';
import ToolsIcon from '@mui/icons-material/Construction';
import RunningIcon from '@mui/icons-material/MonitorHeartOutlined';
import StopIcon from '@mui/icons-material/StopCircle';
import { dashboardDataRequest } from './dashboard.api.caller';
import { PieChart,Pie,Legend } from 'recharts';

export const DashboardComponent = () => {

  const [showComponents, setShowComponents] = React.useState(false);
  const [data, setData] = React.useState(null);

  // eslint-disable-next-line react-hooks/exhaustive-deps
  React.useEffect(async () => {
    if (isUserActive() === false) {
      window.location.href = BASE_URL + URL_MAP.LOGIN;
    } else {

      await dashboardDataRequest(getUserId()).then((data) => {
        setData(data);
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
      {showComponents && <AppbarDrawerComponent activeComponent activeLink={URL_MAP.DASHBOARD} element={<DashboardPanelComponents data={data} />}></AppbarDrawerComponent>}
    </div>
  )
}

const DashboardPanelComponents = (props) => {
  const data = props.data;
  const pieChartData= [
    {name: 'Success', value: data.successCount, fill: "#35A124",label: "asd"},
    {name: 'Failure', value: data.failureCount, fill: "#E83333"},
  ];
    
  return (
    <div>
      <WrapperContainer boxShadow={false} spacing={2}>
        <ContainerItem xs={12} sm={12} md={12} lg={12} xl={12} style={{ marginBottom: "2%", paddingLeft: "1%" }}>
          <Typography variant="h4">Dashboard</Typography>
        </ContainerItem>
        <ContainerItem  xs={12} sm={12} md={6} lg={4} xl={4} style={{ marginBottom: "2%", paddingLeft: "1%" }}>
          <Card sx={{ minWidth: 275 }} style={{background: "#FFC300 "}}>
            <CardContent>
              <ServerIcon style={{ width: "57px", height: "57px" }}></ServerIcon>
              <Typography variant="h5" component="div">
                Total Server: {data.totalServer}
              </Typography>
            </CardContent>
          </Card>
        </ContainerItem>
        <ContainerItem xs={12} sm={12} md={6} lg={4} xl={4} style={{ marginBottom: "2%", paddingLeft: "1%" }}>
          <Card sx={{ minWidth: 270 }} style={{background: "#33E86C"}}>
            <CardContent>
              <RunningIcon style={{ width: "57px", height: "57px" }} />
              <Typography variant="h5" component="div">
                Running Servers: {data.runningServer}
              </Typography>
            </CardContent>
          </Card>
        </ContainerItem>
        <ContainerItem xs={12} sm={12} md={6} lg={4} xl={4} style={{ marginBottom: "2%", paddingLeft: "1%" }}>
          <Card sx={{ minWidth: 270 }}>
            <CardContent style={{background: "#F24834"}}>
              <StopIcon style={{ width: "57px", height: "57px"}} />
              <Typography variant="h5" component="div">
                Stopped Servers: {data.stoppedServer}
              </Typography>
            </CardContent>
          </Card>
        </ContainerItem>
        <ContainerItem xs={12} sm={12} md={6} lg={6} xl={6} style={{ marginBottom: "2%", paddingLeft: "1%" }}>
        <Card sx={{ minWidth: 275 }}>
            <CardContent>
            <Typography variant="h5" component="div">
                Server Access
              </Typography>
            <PieChart width={600} height={300}>
          <Pie data={pieChartData} dataKey="value" outerRadius={130}  innerRadius={70} />
          <Legend layout="horizontal" verticalAlign="top" align="right" />
        </PieChart>      
            </CardContent>
          </Card>
        
        </ContainerItem>
        <ContainerItem xs={12} sm={12} md={6} lg={6} xl={6} style={{ marginBottom: "2%", paddingLeft: "1%" }}>
          <WrapperContainer boxShadow={false}>
          <ContainerItem xs={12} sm={12} md={12} lg={12} xl={12} style={{ marginBottom: "2%", paddingLeft: "1%" }}>
          <Card sx={{ minWidth: 275 }}>
            <CardContent>
              <Typography variant="h5" component="div">
                Quick Access
              </Typography><br />
              <Button onClick={()=>{ window.location.href=BASE_URL+URL_MAP.LAUNCH_SERVER}} color="info" variant="text" style={{ fontSize: "18px", textTransform: "capitalize" }}><AddIcon style={{ fontSize: "20px" }} />&nbsp;Launch Server</Button>
              &nbsp;
              <Button color="info" onClick={()=>{ window.location.href=BASE_URL+URL_MAP.CONFIGURE_SERVER}} variant="text" style={{ fontSize: "18px", textTransform: "capitalize" }}><ToolsIcon style={{ fontSize: "20px" }} />&nbsp;Configure Server</Button>
            </CardContent>
          </Card>
          </ContainerItem>
          <ContainerItem xs={12} sm={12} md={12} lg={6} xl={6} style={{ marginBottom: "2%", paddingLeft: "1%" }}>
          <Card sx={{ minWidth: 275 }}>
            <CardContent>
              <Typography variant="h5" component="div" style={{color: "#900C3F"}}>
                Special Info
              </Typography><br />
              <Typography variant="p" component="div" style={{textAlign: "justify"}}>
                You can also access your server directly using any SSH client tool. Currently we only provide Ubuntu servers you can access it using Putty.
              </Typography><br />
            </CardContent>
          </Card>
          </ContainerItem>
          <ContainerItem xs={12} sm={12} md={12} lg={6} xl={6} style={{ marginBottom: "2%", paddingLeft: "1%" }}>
          <Card sx={{ minWidth: 275 }}>
            <CardContent>
              <Typography variant="h5" component="div" style={{color: "#900C3F"}}>
                Access Server Using SSH
              </Typography><br />
              <Typography variant="body2" component="div" style={{textAlign: "justify"}}>
                1. Download PEM file from cloudeasy portal.
              </Typography>
              <Typography variant="body2" component="div" style={{textAlign: "justify"}}>
                2. Convert PEM file to PPK file using Puttygen.
              </Typography>
              <Typography variant="body2" component="div" style={{textAlign: "justify"}}>
                3. Copy the server ip address from portal and Paste it on the input field Host Name.
              </Typography>
              <Typography variant="body2" component="div" style={{textAlign: "justify"}}>
                4. Go to Auth tab present inside SSH tab upload your PPK file and Click on open.
              </Typography>
              
            </CardContent>
          </Card>
          </ContainerItem>
          </WrapperContainer>
        </ContainerItem>

      </WrapperContainer>
    </div>
  )
}








