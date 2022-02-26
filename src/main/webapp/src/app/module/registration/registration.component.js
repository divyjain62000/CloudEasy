/* eslint-disable jsx-a11y/alt-text */
import React from 'react';
import { Button, FormControl, FormGroup, Hidden, Typography } from '@mui/material';
import { ContainerItem, OutlinedTextFieldWrapper, WrapperContainer } from '../../shared/components/container.component';
import registerImg from '../../assets/register-img.gif';
import { registrationRequest } from '../registration/registration.api.caller';
import { BASE_URL, severity } from '../../constants/app.constants';
import { URL_MAP } from '../../constants/url-mapper';
import { DialogWrapperComponent } from '../../shared/components/dialog.component';

export const RegistrationComponent = () => {

    const [firstName, setFirstName] = React.useState(null);
    const [lastName, setLastName] = React.useState(null);
    const [emailId, setEmailId] = React.useState(null);
    const [mobileNumber, setMobileNumber] = React.useState(null);
    const [password, setPassword] = React.useState(null);
    const [confirmPassword, setConfirmPassword] = React.useState(null);
    const [userErr, setUserErr] = React.useState({});
    const [dialogOpen, setDialogOpen] = React.useState(false);



    const register = async () => {
        const user = {
            firstName: firstName,
            lastName: lastName,
            emailId: emailId,
            mobileNumber: mobileNumber,
            password: password,
            confirmPassword: confirmPassword,
            role: "USER"
        }
        await registrationRequest(user).then((response) => {
            setDialogOpen(true);
        }, (exception) => {
            setUserErr(exception);
        })
            .catch(error => {
                console.error("Error while trying to authenticate user: " + error);
            })
    }




    return (
        <div style={{
            top: 0,
            bottom: 0,
            position: "relative"
        }}
            boxShadow={0}
        >
            <DialogWrapperComponent
                open={dialogOpen}
                severity={severity.SUCCESS}
                message={"Registered Successfully!!"}
                closeAction={() => { window.location.href = BASE_URL + URL_MAP.DASHBOARD; }}
            />
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
                        boxShadow={0}
                    >
                        <Hidden only={['xs', 'sm']}>
                            <ContainerItem lg={6} style={{}}>
                                <img src={registerImg} width="90%" height='110%' />
                            </ContainerItem>
                        </Hidden>

                        <ContainerItem xs={12} sm={12} md={6} lg={6} xl={6}>
                            <FormGroup>
                                <FormControl>
                                    <Typography variant='h3' align='center'>Sign-Up</Typography>
                                </FormControl><br /><br />
                            </FormGroup>
                            <WrapperContainer boxShadow={0} spacing={2}>
                                <ContainerItem xs={12} sm={12} md={6} lg={6} xl={6}>
                                    <FormGroup>
                                        <FormControl>
                                            <OutlinedTextFieldWrapper
                                                label="First Name"
                                                onChange={(ev) => {
                                                    setFirstName(ev.target.value);
                                                }}
                                                value={firstName}
                                                error={userErr.FIRST_NAME_ERR==null?false:true}
                                                helperText={userErr.FIRST_NAME_ERR}
                                            />
                                        </FormControl><br />
                                    </FormGroup>
                                </ContainerItem>
                                <ContainerItem xs={12} sm={12} md={6} lg={6} xl={6}>
                                    <FormGroup>
                                        <FormControl>
                                            <OutlinedTextFieldWrapper
                                                label="Last Name"
                                                onChange={(ev) => {
                                                    setLastName(ev.target.value);
                                                }}
                                                value={lastName}
                                                error={userErr.LAST_NAME_ERR==null?false:true}
                                                helperText={userErr.LAST_NAME_ERR}
                                            />
                                        </FormControl><br />
                                    </FormGroup>
                                </ContainerItem>
                                <ContainerItem xs={12} sm={12} md={6} lg={6} xl={6}>
                                    <FormGroup>
                                        <FormControl>
                                            <OutlinedTextFieldWrapper
                                                label="Email Id"
                                                onChange={(ev) => {
                                                    setEmailId(ev.target.value);
                                                }}
                                                value={emailId}
                                                error={userErr.EMAIL_ID_ERR==null?false:true}
                                                helperText={userErr.EMAIL_ID_ERR}
                                            />
                                        </FormControl><br />
                                    </FormGroup>
                                </ContainerItem>
                                <ContainerItem xs={12} sm={12} md={6} lg={6} xl={6}>
                                    <FormGroup>
                                        <FormControl>
                                            <OutlinedTextFieldWrapper
                                                label="Mobile Number"
                                                onChange={(ev) => {
                                                    setMobileNumber(ev.target.value);
                                                }}
                                                value={mobileNumber}
                                                error={userErr.MOBILE_NUMBER_ERR==null?false:true}
                                                helperText={userErr.MOBILE_NUMBER_ERR}
                                            />
                                        </FormControl><br />
                                    </FormGroup>
                                </ContainerItem>
                                <ContainerItem xs={12} sm={12} md={6} lg={6} xl={6}>
                                    <FormGroup>
                                        <FormControl>
                                            <OutlinedTextFieldWrapper
                                                label="Password"
                                                type="password"
                                                onChange={(ev) => {
                                                    setPassword(ev.target.value);
                                                }}
                                                value={password}
                                                error={userErr.PASSWORD_ERR==null?false:true}
                                                helperText={userErr.PASSWORD_ERR}
                                            />
                                        </FormControl><br />
                                    </FormGroup>
                                </ContainerItem>
                                <ContainerItem xs={12} sm={12} md={6} lg={6} xl={6}>
                                    <FormGroup>
                                        <FormControl>
                                            <OutlinedTextFieldWrapper
                                                label="Confirm Password"
                                                type="password"
                                                onChange={(ev) => {
                                                    setConfirmPassword(ev.target.value);
                                                }}
                                                value={confirmPassword}
                                                error={userErr.CONFIRM_PASSWORD_ERR==null?false:true}
                                                helperText={userErr.CONFIRM_PASSWORD_ERR}
                                            />
                                        </FormControl><br />
                                    </FormGroup>
                                </ContainerItem>
                                <ContainerItem xs={12} sm={12} md={12} lg={12} xl={12}>
                                    <FormGroup>
                                        <FormControl>
                                            <Button variant="contained" color="primary" onClick={register} >Register</Button>
                                        </FormControl><br />
                                    </FormGroup>
                                </ContainerItem>
                                <ContainerItem xs={6} sm={6} md={6} lg={6} xl={6}>
                                    <FormGroup>
                                        <FormControl>
                                            <a href="/login" style={{ textDecoration: "none", cursor: "pointer", fontSize: "18px", paddingRight: "15px", color: "#000" }}>Login</a>
                                        </FormControl><br />
                                    </FormGroup>
                                </ContainerItem>
                                <ContainerItem xs={6} sm={6} md={6} lg={6} xl={6}>
                                    <FormGroup>
                                        <FormControl style={{ textAlign: "right" }}>
                                            <a href="/" style={{ textDecoration: "none", cursor: "pointer", fontSize: "18px", paddingRight: "15px", color: "#000" }}>Home</a>
                                        </FormControl><br />
                                    </FormGroup>
                                </ContainerItem>



                            </WrapperContainer>

                        </ContainerItem>
                        <Hidden only={['md', 'lg', 'xl']}>
                            <ContainerItem lg={6} style={{ textAlign: 'center' }}>
                                <img src={registerImg} width="80%" />
                            </ContainerItem>
                        </Hidden>

                    </WrapperContainer>
                </ContainerItem>
            </WrapperContainer>
        </div>
    );
}