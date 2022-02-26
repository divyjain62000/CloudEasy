import React from 'react';
import { isUserActive } from '../../auth/auth.service';
import { WrapperContainer, ContainerItem, OutlinedTextFieldWrapper } from '../../../shared/components/container.component';
import { BASE_URL } from '../../../constants/app.constants';
import { URL_MAP } from '../../../constants/url-mapper';
import { AppbarDrawerComponent } from '../../../shared/components/appbar-drawer.component';
import { Typography, Button} from '@mui/material';
import FormGroup from '@mui/material/FormGroup';
import { FormControl } from '@mui/material';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import { uploadStaticWebsiteRequest } from './file-upload-download.api.caller';
import { severity } from '../../../constants/app.constants';
import { LoaderComponent } from '../../../shared/components/loader.component';
import { DialogWrapperComponent } from '../../../shared/components/dialog.component';
import { extractInstanceId } from '../instance-helper';


export const UploadStaticWebsite = () => {

  const [showComponents, setShowComponents] = React.useState(false);




  React.useEffect(() => {
    if (isUserActive() === false) {
      window.location.href = BASE_URL + URL_MAP.LOGIN;
    } else {

      setShowComponents(true);
    }
  }, []);
  return (
    <div>
      {showComponents && <AppbarDrawerComponent activeComponent activeLink={URL_MAP.HOST_STATIC_WEBSITE} element={<UploadComponent />}></AppbarDrawerComponent>}
    </div>
  )
}




const UploadComponent = (props) => {

  const [uploadInstanceId, setUploadInstanceId] = React.useState("");
  const [fileToUpload, setFileToUpload] = React.useState(null);
  const [fileExtensionErr,setFileExtensionErr] = React.useState(null);
  const [uploadErr, setUploadErr] = React.useState({});
  const [uploadIdErr, setUploadIdErr] = React.useState(false);

  const [progressLoader, setProgressLoader] = React.useState(false);
  const [progressMessage, setProgressMessage] = React.useState("");
  const [dialogOpen, setDialogOpen] = React.useState(false);
  const [dialogMessage, setDialogMessage] = React.useState("");
  const [variant, setVariant] = React.useState("");


  const uploadFile = async () => {
    if(fileExtensionErr===false){
    const formData = new FormData();
    formData.append('fileToUpload', fileToUpload);
    formData.append('instanceId', extractInstanceId(uploadInstanceId));
    setProgressLoader(true);
    setProgressMessage("Hosting your website...");
    await uploadStaticWebsiteRequest(formData).then((response) => {
      setProgressLoader(false);
      setProgressMessage("");
      setVariant(severity.SUCCESS);
      setDialogOpen(true);
      setDialogMessage("Website Hosted Successfully!!");
    }, (exception) => {
      setUploadIdErr(true);
      setUploadErr(exception);
      setProgressLoader(false);
      setProgressMessage("");
      setDialogOpen(false);
    }).catch((error) => {
      setDialogOpen(false);
    });
  }
  }



  return (
    <div>
      <LoaderComponent open={progressLoader} message={progressMessage} />
      <DialogWrapperComponent open={dialogOpen} message={dialogMessage} severity={variant} closeAction={() => {
        setDialogOpen(false);
        window.location.reload(false);
      }}
      />
      <WrapperContainer boxShadow={false} spacing={2}>
        <ContainerItem xs={12} sm={12} md={12} lg={12} xl={12} style={{ marginBottom: "2%", paddingLeft: "1%" }}>
          <Typography variant="h4">Host Static Website</Typography>
        </ContainerItem>
        <ContainerItem xs={12} sm={12} md={12} lg={12} xl={12}>
          <Card sx={{ minWidth: 265 }} style={{ boxShadow: "none", border: "1px solid #DDD" }}>
            <CardContent style={{ textAlign: "center" }}>
              <Typography variant="h5">Upload File</Typography><br />
              <Typography color="error" variant="h6">*Note: Only zip file is allow</Typography>
              <Typography color="error" variant="h6">**Note: Auto Config your server before hosting website</Typography>
            </CardContent>
            <CardContent style={{ textAlign: "center" }}>
              <FormControl>
                <FormGroup>
                  <OutlinedTextFieldWrapper
                    label="Server Id"
                    onChange={(ev) => {
                      setUploadInstanceId(ev.target.value);
                    }}
                    error={uploadIdErr}
                    helperText={uploadErr.ID_ERR}
                    value={uploadInstanceId}
                  />
                </FormGroup><br />
                <FormGroup>
                  <OutlinedTextFieldWrapper
                    type="file"
                    onChange={(ev) => {
                      setFileToUpload(ev.target.files[0]);
                      if(ev.target.value.substring(ev.target.value.lastIndexOf('.') + 1)!=="zip") {
                        setFileExtensionErr(true);
                      }else {
                        setFileExtensionErr(false);
                      }
                    }}
                    error={fileExtensionErr}
                    helperText={fileExtensionErr?"Only zip file is allowed":""}
                  />
                </FormGroup>
              </FormControl>
            </CardContent>

            <CardContent style={{ textAlign: "center" }}>
              <Typography variant="h6"><Button onClick={uploadFile} variant="contained">Host</Button></Typography>
            </CardContent>
          </Card>
        </ContainerItem>

      </WrapperContainer>

    </div>
  )
}
