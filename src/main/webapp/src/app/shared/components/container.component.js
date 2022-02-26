import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import { MenuItem } from '@mui/material';


export const ContainerItem = (props) => {
    return (
      <Grid item shadows {...props} />
    )
  }
  
  export const WrapperContainer = (props) => {
    return (
      <Box component={Grid} boxShadow={3} container {...props} />
    )
  }


  export const OutlinedTextFieldWrapper = (props) => {
      return(
        <TextField 
            {...props}
            variant="outlined"
        />
      )
  }

  export const OutlinedSelectWrapper = (props) => {
    return(
      <TextField 
          {...props}
          variant="outlined"
          value={props.defaultValue}
          select
      >
          {
        props.menulist.map((item) => {
          return (
            <MenuItem key={item.id} value={item.id}>{item.name}</MenuItem>
          );
        })
      }
    </TextField>
    )
}

export const NewlineText = (props) => {
  const text = props.text;
  const newText = text.split('\n').map(str => <p>{str}</p>);
  return newText;
}
