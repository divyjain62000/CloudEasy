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
import { downloadFileRequest, uploadFileRequest } from './file-upload-download.api.caller';
import { severity } from '../../../constants/app.constants';
import { LoaderComponent } from '../../../shared/components/loader.component';
import { DialogWrapperComponent } from '../../../shared/components/dialog.component';
import { extractInstanceId } from '../instance-helper';


export const FileUploadAndDownloadComponent = () => {

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
      {showComponents && <AppbarDrawerComponent activeComponent activeLink={URL_MAP.FILE_ACCESS} element={<UploadAndDownloadComponent />}></AppbarDrawerComponent>}
    </div>
  )
}




const UploadAndDownloadComponent = (props) => {

  const [uploadInstanceId, setUploadInstanceId] = React.useState("");
  const [fileToUpload, setFileToUpload] = React.useState(null);
  const [uploadErr, setUploadErr] = React.useState({});
  const [uploadIdErr, setUploadIdErr] = React.useState(false);

  const [progressLoader, setProgressLoader] = React.useState(false);
  const [progressMessage, setProgressMessage] = React.useState("");
  const [dialogOpen, setDialogOpen] = React.useState(false);
  const [dialogMessage, setDialogMessage] = React.useState("");
  const [variant, setVariant] = React.useState("");


  const [downloadInstanceId, setDownloadInstanceId] = React.useState("");
  const [filePath, setFilePath] = React.useState(null);

  const uploadFile = async () => {
    const formData = new FormData();
    formData.append('fileToUpload', fileToUpload);
    formData.append('instanceId', extractInstanceId(uploadInstanceId));
    setProgressLoader(true);
    setProgressMessage("Uploading...");
    await uploadFileRequest(formData).then((response) => {
      setProgressLoader(false);
      setProgressMessage("");
      setVariant(severity.SUCCESS);
      setDialogOpen(true);
      setDialogMessage("File Uploaded Successfully!!");
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


  const downloadFile = async () => {

    setProgressLoader(true);
    setProgressMessage("downloading...");
    await downloadFileRequest(filePath, extractInstanceId(downloadInstanceId)).then((response) => {
      setProgressLoader(false);
      setProgressMessage("");
      setDialogOpen(false);
      window.location.reload(false);
    }, (exception) => {
      setProgressLoader(false);
      setProgressMessage("");
      setDialogOpen(false);
    }).catch((error) => {
      setDialogOpen(false);
    });

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
          <Typography variant="h4">File Access</Typography>
        </ContainerItem>
        <ContainerItem xs={12} sm={12} md={12} lg={6} xl={6}>
          <Card sx={{ minWidth: 265 }} style={{ boxShadow: "none", border: "1px solid #DDD" }}>
            <CardContent style={{ textAlign: "center" }}>
              <Typography variant="h5">Upload File</Typography><br />
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
                    }}
                  />
                </FormGroup>
              </FormControl>
            </CardContent>

            <CardContent style={{ textAlign: "center" }}>
              <Typography variant="h6"><Button onClick={uploadFile} variant="contained">Upload</Button></Typography>
            </CardContent>
          </Card>
        </ContainerItem>

        <ContainerItem xs={12} sm={12} md={12} lg={6} xl={6}>
          <Card sx={{ minWidth: 265 }} style={{ boxShadow: "none", border: "1px solid #DDD" }}>
            <CardContent style={{ textAlign: "center" }}>
              <Typography variant="h5">Download File</Typography><br />
            </CardContent>
            <CardContent style={{ textAlign: "center" }}>
              <FormControl>
                <FormGroup>
                  <OutlinedTextFieldWrapper
                    label="Server Id"
                    onChange={(ev) => {
                      setDownloadInstanceId(ev.target.value);
                    }}
                  />
                </FormGroup><br />
                <FormGroup>
                  <OutlinedTextFieldWrapper
                    label="File Name"
                    onChange={(ev) => {
                      setFilePath(ev.target.value);
                    }}
                  />
                </FormGroup>
              </FormControl>
            </CardContent>

            <CardContent style={{ textAlign: "center" }}>
              <Typography variant="h6"><Button onClick={downloadFile} variant="contained">Download</Button></Typography>
            </CardContent>
          </Card>
        </ContainerItem>

      </WrapperContainer>

    </div>
  )
}
