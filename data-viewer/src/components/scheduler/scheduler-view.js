import SchedulerTable from "./scheduler-table";
import SchedulerModify from "./scheduler-modify";
import { Button } from "@mui/material";
import { createContext, useContext, useState } from "react";

import "./scheduler-view.css";
import { properties } from "../../properties";

export const TaskContext = createContext({});

export default function ScheduleView()
{
    const [task, setTask] = useState({});
    const [modifyState, setModifyState] = useState(false);

    const handleClick = () =>
    {
        //let today = new Date();
        //console.log(today.getDate() - 1);
        let yesterday = new Date(Date.now() - 86400000);
        let date = yesterday.toISOString().split("T")[0]
        let URL = properties.DATA_IMPORTER_DAILY_URI.concat(date);

        fetch(URL).then(response =>
            {
                response.ok ? console.log("Succesful Dowload") : console.log("Failed to Dowload");
            }).catch(err => console.error(err));
    }

    return(
        <div className="scheduler-view">
            <TaskContext.Provider value={{task, setTask, modifyState, setModifyState}}>
            <div className="column">
                <SchedulerTable/>
            </div>
            <div className="column">
                <div className="download-button">
                    <Button size="large" variant="contained" onClick={handleClick}>Download now</Button>
                </div>
                <div className="modify-table">
                    <SchedulerModify/>
                </div>
            </div>
            </TaskContext.Provider>
        </div>
    )
}