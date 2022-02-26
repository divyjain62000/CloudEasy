import * as React from 'react';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import { ContainerItem, WrapperContainer } from '../../shared/components/container.component';
import homeImg from '../../assets/home-img.gif';
import homeImg2 from '../../assets/home-img2.gif';
import { Button } from '@mui/material';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import EmailIcon from '@mui/icons-material/Email';
import MobileIcon from '@mui/icons-material/PhoneAndroid';
import Icon1 from '@mui/icons-material/Help';
import Icon2  from '@mui/icons-material/Language';
import Icon3 from '@mui/icons-material/Mouse';
import Icon4 from '@mui/icons-material/CloudUpload';
import Icon5 from '@mui/icons-material/Dns';
import Icon6 from '@mui/icons-material/Downloading';
import Icon7 from '@mui/icons-material/Public';
import Icon8 from '@mui/icons-material/LocationOn';
import {BASE_URL} from '../../constants/app.constants'; 
import {URL_MAP} from '../../constants/url-mapper';


export const HomeComponent = () => {
  return (
    <div>
      <DenseAppBar />
      <WrapperContainer boxShadow={false} style={{ background: "#DDD" }}>

        <ContainerItem xs={12} sm={12} md={12} lg={4} xl={4} style={{ color: "#000000", border: '1px solid #DDD', textAlign: "center", backgroundImage: "url(" + homeImg + ")", backgroundRepeat: "no-repeat", height: "450px", marginTop: "20px" }}>

        </ContainerItem>
        <ContainerItem xs={12} sm={12} md={12} lg={4} xl={4} style={{ paddingTop: "5%", color: "#000000", border: '1px solid #DDD', textAlign: "center", marginTop: "30px" }}>
          <Typography variant="h3" color="primary" >CloudEasy</Typography>
          <h2>Make your life easy with CloudEasy</h2>
          <Button color="secondary" onClick={ ()=>{window.location.href=BASE_URL+URL_MAP.LOGIN;}} variant='contained'>Sign In</Button>&nbsp;&nbsp;&nbsp;&nbsp;
          <Button color="warning" onClick={ ()=>{window.location.href=BASE_URL+URL_MAP.REGISTER;}} variant='contained'>Sign up</Button>
          <br /><br />
        </ContainerItem>
        <ContainerItem xs={12} sm={12} md={12} lg={4} xl={4} style={{ color: "#000000", border: '1px solid #DDD', textAlign: "center", backgroundImage: "url(" + homeImg2 + ")", backgroundRepeat: "no-repeat", height: "450px", marginTop: "20px" }}>
        </ContainerItem>
      </WrapperContainer><br />
      <center><Typography variant="h4" style={{color: "#9c27b0"}} component="div">Our Services</Typography></center>
        
      <WrapperContainer boxShadow={false} spacing={5} style={{padding: "10px"}}>
        <ContainerItem xs={12} sm={12} md={12} lg={4} xl={4}>
          <Card  sx={{ minWidth: 270 }}>
            <CardContent style={{ background: "#1976d2" }}>
              <Typography variant="h5" style={{color: "white",textAlign: "center"}} component="div">
              <Icon1 style={{width: "30%",height: "30%"}}/><br />
                Help &amp; Advice
              </Typography>
            </CardContent>
          </Card>
        </ContainerItem>
        <ContainerItem xs={12} sm={12} md={12} lg={4} xl={4}>
          <Card sx={{ minWidth: 270 }}>
            <CardContent style={{ background: "#CCC",textAlign: "center",color: "#000000" }}>
              <Typography variant="h5" component="div">
              <Icon2 style={{width: "30%",height: "30%"}}/><br />
                Host Static Websites
              </Typography>
            </CardContent>
          </Card>
        </ContainerItem>
        <ContainerItem xs={12} sm={12} md={12} lg={4} xl={4}>
          <Card sx={{ minWidth: 270 }}>
            <CardContent style={{ background: "#1976d2" }}>
            <Typography variant="h5" style={{color: "white",textAlign: "center"}} component="div">
            <Icon3 style={{width: "30%",height: "30%"}}/><br />
                Configure Server On Simple Clicks
              </Typography>
            </CardContent>
          </Card>
        </ContainerItem>
        <ContainerItem xs={12} sm={12} md={12} lg={4} xl={4}>
          <Card sx={{ minWidth: 270 }}>
            <CardContent style={{ background: "#CCC",textAlign: "center",color: "#000000" }}>
              <Typography variant="h5" component="div">
              <Icon4 style={{width: "30%",height: "30%"}}/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<Icon6 style={{width: "30%",height: "30%"}}/><br />
                Upload &amp; Download Files
              </Typography>
            </CardContent>
            </Card>
        </ContainerItem>
        <ContainerItem xs={12} sm={12} md={12} lg={4} xl={4}>
          <Card sx={{ minWidth: 270 }}>
            <CardContent style={{ background: "#1976d2" }}>
            <Typography variant="h5" style={{color: "white",textAlign: "center"}} component="div">
            <Icon5 style={{width: "30%",height: "30%"}}/><Icon5 style={{width: "20%",height: "30%"}}/><Icon5 style={{width: "30%",height: "30%"}}/><br />
                Create Multiple Servers
              </Typography>
            </CardContent>
          </Card>
        </ContainerItem>
          <ContainerItem xs={12} sm={12} md={12} lg={4} xl={4}>    
          <Card sx={{ minWidth: 270 }}>
            <CardContent style={{ background: "#CCC",textAlign: "center",color: "#000000" }}>
            <Icon7 style={{width: "20%",height: "30%"}}/><Icon8 style={{width: "30%",height: "30%"}}/><br/>
            
            
              <Typography variant="h5" component="div">
                Access Server From Anywhere
              </Typography>
            </CardContent>
          </Card>
        </ContainerItem>
      </WrapperContainer>
<br/><br/>
      <WrapperContainer>
      <ContainerItem color="primary" xs={12} sm={12} md={12} lg={12} xl={12}>
      <Card sx={{ minWidth: 270 }} style={{borderRadius: "none"}}>
            <CardContent style={{ background: "#1976d2" }}>
            <Typography variant="body2" style={{color: "white",textAlign: "center"}} component="div">
            <Button color="inherit" variant='string' style={{textTransform: "none",color: "#000000",marginBottom: "-20px",float: "right"}}><EmailIcon />&nbsp;divy.jain6200@gmail.com</Button>            
            <br /><br />            
            <Button color="inherit" variant='string' style={{textTransform: "none",color: "#000000",marginBottom: "-20px",float: "right"}}><MobileIcon />&nbsp;9329940041</Button>
            <br /><br />
            <Button color="inherit" variant='string' style={{textTransform: "none",color: "#000000",marginBottom: "-20px"}}>&copy; 2022 Divy Jain. All rights are reserverd</Button>
              </Typography>
            </CardContent>
          </Card>
          </ContainerItem>
      </WrapperContainer>
      

    </div>
  )
}

const DenseAppBar = (props) => {
  return (
    <Box sx={{ flexGrow: 1 }}>
      <AppBar position="static" style={{ background: "#DDD" }}>
        <Toolbar>
          <Typography variant="h6" color="primary" sx={{ flexGrow: 1 }}>
            CloudEasy
          </Typography>
          <Button color="primary" variant='text' style={{textTransform: "none"}}><EmailIcon />&nbsp;cloudeasy.support.com</Button>
        </Toolbar>
      </AppBar>
    </Box>
  );
}
