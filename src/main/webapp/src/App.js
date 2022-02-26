import React from 'react';
import { BrowserRouter, Route, Routes} from "react-router-dom";
import { URL_COMPONENT_MAPPER_LIST } from './app/constants/url-mapper';



function App() {

  
  return (

    <BrowserRouter>
      <Routes>
        {
          URL_COMPONENT_MAPPER_LIST.map((elem)=>{
            return(
              <Route exact path={elem.path} element={elem.component}></Route>
            )
          })
        }
        </Routes>
    </BrowserRouter>

  );
}

export default App;
