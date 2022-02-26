/* eslint-disable jsx-a11y/alt-text */
import React from 'react';
import { Button, FormControl, FormGroup, Hidden, Snackbar, Typography } from '@mui/material';
import { ContainerItem, OutlinedTextFieldWrapper, WrapperContainer } from '../../shared/components/container.component';
import loginImg from '../../assets/login-img.gif';
import { userErr } from '../../constants/error/user.error';
import { loginRequest } from './login.api.caller';
import { BASE_URL } from '../../constants/app.constants';
import { URL_MAP } from '../../constants/url-mapper';
import Alert from '@mui/material/Alert'


export const LoginComponent= () =>{


    const [emailId,setEmailId]=React.useState("");
    const [password,setPassword]=React.useState("");
    const [emailIdErr,setEmailIdErr]=React.useState("");
    const [passwordErr,setPasswordErr]=React.useState("");
    const [errorMsg,setErrorMsg]=React.useState("");
    const [snackbarOpen,setSnackbarOpen]=React.useState(false);

    const vertical='top';
    const horizontal='center';



    const login= async ()=>{

        let flag=false;
        if(emailId==null || emailId.length===0) {
            flag=true;
            setEmailIdErr(userErr.EMAIL_ID_REQUIRED);
        }else {
            setEmailIdErr("");
        }
        if(password==null || password.length===0) {
            flag=true;
            setPasswordErr(userErr.PASSWORD_REQUIRED);
        }else {
            setPasswordErr("");
        } 

        if(!flag) {
            const credentials={
                username: emailId,
                password: password
            };
            await loginRequest(credentials).then((response)=>{
                sessionStorage.setItem("token",response.token);
                localStorage.setItem("token",response.token);
                window.location.href = BASE_URL + URL_MAP.DASHBOARD;
            },(exception)=>{
                setErrorMsg(exception);
                setSnackbarOpen(true);
            })
            .catch(error=>{
                console.error("Error while trying to authenticate user: " + error);
            })
            


        }
    }



        return (
          <div style={{
            top: 0,
            bottom: 0,
            position: "relative"
        }}
            boxShadow={"false"}
        >
            <WrapperContainer style={{
                flexGrow: 1,
                // border: "1px solid red",
                paddingLeft: "20px",
                paddingRight: "20px",
                paddingTop: "50px",
                paddingBottom: "20px",
                display: 'flex',
                boxShadow: "none",
            }}>
             
    <div>
      <Snackbar
        anchorOrigin={{ vertical, horizontal }}
        open={snackbarOpen}
        
        key={vertical + horizontal}
        ContentProps={{
            "aria-describedby": "message-id",
            style: {background: "#fbc7c6", color: "red"}
          }}
      >
          <Alert onClose={()=>{ setSnackbarOpen(false); }} severity="error">
          {errorMsg}
        </Alert>
      </Snackbar>
    </div>
                <ContainerItem lg={12} >
                    <WrapperContainer style={{
                        paddingRight: "30px",
                        paddingLeft: "30px",
                        paddingBottom: "30px",
                        paddingTop: "20px",
                        marginTop: "50px",
                        marginBottom: "60px",
                        display: 'flex',

                    }}
                        boxShadow={false}
                    >
                        <Hidden only={['xs', 'sm']}>
                            <ContainerItem lg={6} style={{ }}>
                                <img src={loginImg} width="90%" />
                            </ContainerItem>
                        </Hidden>

                        <ContainerItem xs={12} sm={12} md={6} lg={6} xl={6}>
                        <FormGroup>
                                        <FormControl>
                                            <Typography variant='h3' align='center'>Sign-In</Typography>
                                        </FormControl><br /><br />
                                    </FormGroup>
                            <WrapperContainer boxShadow={false} spacing={2}>
                                <ContainerItem xs={12} sm={12} md={12} lg={12} xl={12}>
                                    <FormGroup>
                                        <FormControl>
                                            <OutlinedTextFieldWrapper
                                                label="Email Id"
                                                onChange={(ev)=>{
                                                    setEmailId(ev.target.value);
                                                }}
                                                value={emailId}
                                                error={emailIdErr.length!==0}
                                                helperText={emailIdErr}
                                            />
                                        </FormControl><br />
                                    </FormGroup>
                                </ContainerItem>
                               
                                <ContainerItem xs={12} sm={12} md={12} lg={12} xl={12}>
                                    <FormGroup>
                                        <FormControl>
                                            <OutlinedTextFieldWrapper
                                                label="Password"
                                                type="password"
                                                onChange={(ev)=>{
                                                    setPassword(ev.target.value);
                                                }}
                                                value={password}
                                                error={passwordErr.length!==0}
                                                helperText={passwordErr}
                                            />
                                        </FormControl><br />
                                    </FormGroup>
                                </ContainerItem>
                               
                                <ContainerItem xs={12} sm={12} md={12} lg={12} xl={12}>
                                    <FormGroup>
                                        <FormControl>
                                            <Button variant="contained" color="primary" onClick={login} >Login</Button>
                                        </FormControl><br />
                                    </FormGroup>
                                </ContainerItem>
                                <ContainerItem xs={6} sm={6} md={6} lg={6} xl={6}>
                                    <FormGroup>
                                        <FormControl>
                                              <a href="/register" style={{textDecoration: "none",cursor: "pointer",fontSize: "18px",paddingRight: "15px",color: "#000"}}>Register</a>
                                        </FormControl><br />
                                    </FormGroup>
                                </ContainerItem>
                                <ContainerItem xs={6} sm={6} md={6} lg={6} xl={6}>
                                    <FormGroup>
                                        <FormControl style={{textAlign: "right"}}>
                                        <a href="/" style={{textDecoration: "none",cursor: "pointer",fontSize: "18px",paddingRight: "15px",color: "#000"}}>Home</a>
                                        </FormControl><br />
                                    </FormGroup>
                                </ContainerItem>


                            </WrapperContainer>
                            
                        </ContainerItem>
                        <Hidden only={['md', 'lg', 'xl']}>
                            <ContainerItem lg={6} style={{ textAlign: 'center' }}>
                                <img src={loginImg} width="80%" />
                            </ContainerItem>
                        </Hidden>

                    </WrapperContainer>
                </ContainerItem>
            </WrapperContainer>
        </div>
          );
}