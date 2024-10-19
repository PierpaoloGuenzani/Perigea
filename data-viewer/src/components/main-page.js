import { useState } from "react";
import DataView from "./data-viewer/data-view";
import ScheduleView from "./scheduler/scheduler-view";
import { Button } from "@mui/material";

export default function MainPage()
{
    const [dataView, setDataView]  = useState(false);

    function onClickSchedule()
    {
      console.log("schedule click");
      setDataView(false);
    }
    function onClickData()
    {
      console.log("Date click");
      setDataView(true);
    }

    return(
    <div className="main-page">
        <div className="button">
            <Button variant='contained' size='large' onClick={onClickSchedule}>Schedule</Button>
            <Button variant='contained' size='large'onClick={onClickData}>Data View</Button>
        </div>
        {dataView? <DataView/> : <ScheduleView/>}
    </div>
    )
}