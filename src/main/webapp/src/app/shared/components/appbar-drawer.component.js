import * as React from 'react';
import { styled, useTheme } from '@mui/material/styles';
import Box from '@mui/material/Box';
import MuiDrawer from '@mui/material/Drawer';
import MuiAppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import List from '@mui/material/List';
import CssBaseline from '@mui/material/CssBaseline';
import Typography from '@mui/material/Typography';
import Divider from '@mui/material/Divider';
import IconButton from '@mui/material/IconButton';
import MenuIcon from '@mui/icons-material/Menu';
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import ListItem from '@mui/material/ListItem';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import DashboardIcon from '@mui/icons-material/Dashboard';
import UserIcon from '@mui/icons-material/AccountCircle';
import ServerIcon from '@mui/icons-material/Storage';
import ToolsIcon from '@mui/icons-material/Construction';
import AddIcon from '@mui/icons-material/AddToQueue';
import LogoutIcon from '@mui/icons-material/Logout';
import HelpCenterIcon from '@mui/icons-material/HelpCenter';
import { getUserName, isUserActive } from '../../module/auth/auth.service';
import { BASE_URL } from '../../constants/app.constants';
import FileAccessIcon from '@mui/icons-material/Folder';
import { URL_MAP } from '../../constants/url-mapper';
import { logout } from '../../module/auth/logout.service';
import WebHostIcon from '@mui/icons-material/Language';


const drawerWidth = 240;

const openedMixin = (theme) => ({
  width: drawerWidth,
  transition: theme.transitions.create('width', {
    easing: theme.transitions.easing.sharp,
    duration: theme.transitions.duration.enteringScreen,
  }),
  overflowX: 'hidden',
});

const closedMixin = (theme) => ({
  transition: theme.transitions.create('width', {
    easing: theme.transitions.easing.sharp,
    duration: theme.transitions.duration.leavingScreen,
  }),
  overflowX: 'hidden',
  width: `calc(${theme.spacing(7)} + 1px)`,
  [theme.breakpoints.up('sm')]: {
    width: `calc(${theme.spacing(9)} + 1px)`,
  },
});

const DrawerHeader = styled('div')(({ theme }) => ({
  display: 'flex',
  alignItems: 'center',
  justifyContent: 'flex-end',
  padding: theme.spacing(0, 1),
  // necessary for content to be below app bar
  ...theme.mixins.toolbar,
}));

const AppBar = styled(MuiAppBar, {
  shouldForwardProp: (prop) => prop !== 'open',
})(({ theme, open }) => ({
  zIndex: theme.zIndex.drawer + 1,
  transition: theme.transitions.create(['width', 'margin'], {
    easing: theme.transitions.easing.sharp,
    duration: theme.transitions.duration.leavingScreen,
  }),
  ...(open && {
    marginLeft: drawerWidth,
    width: `calc(100% - ${drawerWidth}px)`,
    transition: theme.transitions.create(['width', 'margin'], {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.enteringScreen,
    }),
  }),
}));

const Drawer = styled(MuiDrawer, { shouldForwardProp: (prop) => prop !== 'open' })(
  ({ theme, open }) => ({
    width: drawerWidth,
    flexShrink: 0,
    whiteSpace: 'nowrap',
    boxSizing: 'border-box',
    ...(open && {
      ...openedMixin(theme),
      '& .MuiDrawer-paper': openedMixin(theme),
    }),
    ...(!open && {
      ...closedMixin(theme),
      '& .MuiDrawer-paper': closedMixin(theme),
    }),
  }),
);


export const AppbarDrawerComponent = (props) => {

  const theme = useTheme();
  const [open, setOpen] = React.useState(false);

  const handleDrawerOpen = () => {
    setOpen(true);
  };

  const handleDrawerClose = () => {
    setOpen(false);
  };


  return (
    <Box sx={{ display: 'flex' }}>
      <CssBaseline />
      <AppBar position="fixed" open={open}>
        <Toolbar>

          <IconButton
            color="inherit"
            aria-label="open drawer"
            onClick={handleDrawerOpen}
            edge="start"
            sx={{
              marginRight: '36px',
              ...(open && { display: 'none' }),
            }}
          >
            <MenuIcon />

          </IconButton>
          <Typography variant="h6" noWrap component="div">
            CLOUDEASY
          </Typography>
        </Toolbar>
      </AppBar>
      <Drawer variant="permanent" open={open}>
        <DrawerHeader>
          <IconButton onClick={handleDrawerClose}>
            {theme.direction === 'rtl' ? <ChevronRightIcon /> : <ChevronLeftIcon />}
          </IconButton>
        </DrawerHeader>

        <Divider />
        <List>
          <ListItem button key={isUserActive() ? getUserName() : "Guest Login"}>
            <ListItemIcon>
              <UserIcon />
            </ListItemIcon>
            <ListItemText primary={isUserActive() ? getUserName() : "Guest Login"} />
          </ListItem>
        </List>
        <Divider />

        <List>
          <ListItem button onClick={() => {
            window.location.href = BASE_URL + URL_MAP.DASHBOARD;
          }} key={"Dashboard"}>
            <ListItemIcon>
              <DashboardIcon style={{color: props.activeLink===URL_MAP.DASHBOARD?"#1976d2": "grey"}}></DashboardIcon>
            </ListItemIcon>
            <ListItemText style={{color: props.activeLink===URL_MAP.DASHBOARD?"#1976d2": "grey"}} primary={"Dashboard"} />
          </ListItem>
          <ListItem style={{color: "red"}} button onClick={() => {
            window.location.href = BASE_URL + URL_MAP.LAUNCH_SERVER;
          }} key={"Launch Server"}>
            <ListItemIcon>
              <AddIcon style={{color: props.activeLink===URL_MAP.LAUNCH_SERVER?"#1976d2": "grey"}} />
            </ListItemIcon>
            <ListItemText style={{color: props.activeLink===URL_MAP.LAUNCH_SERVER?"#1976d2": "grey"}} primary={"Launch Server"} />
          </ListItem>
          <ListItem button onClick={() => {
            window.location.href = BASE_URL + URL_MAP.SERVERS;
          }} key={"Servers"}>
            <ListItemIcon>
              <ServerIcon style={{color: props.activeLink===URL_MAP.SERVERS?"#1976d2": "grey"}}></ServerIcon>
            </ListItemIcon>
            <ListItemText style={{color: props.activeLink===URL_MAP.SERVERS?"#1976d2": "grey"}} primary={"Servers"} />
          </ListItem>
          <ListItem button onClick={() => {
            window.location.href = BASE_URL + URL_MAP.CONFIGURE_SERVER;
          }} key={"Configure Server"}>
            <ListItemIcon>
              <ToolsIcon style={{color: props.activeLink===URL_MAP.CONFIGURE_SERVER?"#1976d2": "grey"}}></ToolsIcon>
            </ListItemIcon>
            <ListItemText style={{color: props.activeLink===URL_MAP.CONFIGURE_SERVER?"#1976d2": "grey"}} primary={"Configure Server"} />
          </ListItem>
          <ListItem button onClick={() => {
            window.location.href = BASE_URL + URL_MAP.FILE_ACCESS;
          }} key={"File Access"}>
            <ListItemIcon>
              <FileAccessIcon style={{color: props.activeLink===URL_MAP.FILE_ACCESS?"#1976d2": "grey"}}></FileAccessIcon>
            </ListItemIcon>
            <ListItemText style={{color: props.activeLink===URL_MAP.FILE_ACCESS?"#1976d2": "grey"}} primary={"File Access"} />
          </ListItem>
          <ListItem button onClick={() => {
            window.location.href = BASE_URL + URL_MAP.HOST_STATIC_WEBSITE;
          }} key={"Host Static Website"}>
            <ListItemIcon>
              <WebHostIcon style={{color: props.activeLink===URL_MAP.HOST_STATIC_WEBSITE?"#1976d2": "grey"}}></WebHostIcon>
            </ListItemIcon>
            <ListItemText style={{color: props.activeLink===URL_MAP.HOST_STATIC_WEBSITE?"#1976d2": "grey"}} primary={"Host Static Website"} />
          </ListItem>
        </List>

        <List></List>
        <List></List>
        <List></List>
        <List></List>
        <List></List>
        <List></List>
        <List></List>
        <List></List>
        <List></List>
        <List></List>
        <Divider />
        <List></List>
        <List >
          <ListItem button key={"Help Center"} style={{ background: "#3020E2", color: "white" }}>
            <ListItemIcon>
              <HelpCenterIcon style={{ color: "white" }}></HelpCenterIcon>
            </ListItemIcon>
            <ListItemText primary={"Help Center"} />
          </ListItem>
          <Divider />
          <Divider />
          <Divider />
          <ListItem button onClick={logout} key={"Logout"} style={{ background: "#DA2D2D", color: "white" }}>
            <ListItemIcon>
              <LogoutIcon style={{ color: "white" }}></LogoutIcon>
            </ListItemIcon>
            <ListItemText primary={"Logout"} />
          </ListItem>
        </List>
      </Drawer>
      <Box component="main" sx={{ flexGrow: 1, p: 3 }}>
        <DrawerHeader />
        {props.element}
      </Box>
    </Box>
  );

}