import React, { useEffect } from "react";
import { connect } from "react-redux";
import { makeStyles } from '@material-ui/core/styles';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import AddIcon from '@material-ui/icons/Add';
import EditIcon from '@material-ui/icons/Edit';
import ListItemText from '@material-ui/core/ListItemText';
import Divider from '@material-ui/core/Divider';

import {getAllBrands} from '../../../store/actions/carInfo'

const useStyles = makeStyles((theme) => ({
  root: {
    width: '100%',
    maxWidth: 360, 
    backgroundColor: theme.palette.background.paper,
  },
}));

const ModelList = ({
allModels,
setSelectedModel,
selectedModel,
handleClickModel

}) => {
  const classes = useStyles();
  const [selectedIndex, setSelectedIndex] = React.useState(null);

  const handleListItemClick = (event, model, index) => {
    console.log(index);
    setSelectedIndex(index);
    setSelectedModel(model);
    handleClickModel(event, model);
  };

  return (
    <div className={classes.root}>
      {allModels && (
        <List component="nav" aria-label="main mailbox folders">
        
        {allModels
            .map((model, index) => {
                return(
                <ListItem
                    button
                    selected={selectedIndex === index}
                    onClick = {(event) => handleListItemClick(event, model, index)}
                    >
                    <ListItemText primary={model.modelName} />
                    </ListItem>);
                
        })}
        <Divider/>
        
        <ListItem
          button
          selected={selectedIndex === allModels.length}
          onClick={(event) => handleListItemClick(event, "newModel", allModels.length)}
        >
          <ListItemIcon>
            <AddIcon/>
          </ListItemIcon>
          <ListItemText primary="Add new model" />
        </ListItem>
      </List>
      )}
    </div>
  );
}

const mapStateToProps = (state) => ({
    currentUser: state.user.user,
  });
  
  export default connect(mapStateToProps)(ModelList);