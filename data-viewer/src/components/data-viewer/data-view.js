import { useState } from "react";
import ExchangeRateTable from "./exchange-rate-table";
import HistoricalTable from "./historical-table";
import DailyTable from "./daily-table";
import { RadioGroup, FormControlLabel, Radio } from "@mui/material";

export default function DataView()
{
    const [state, setState] = useState(<ExchangeRateTable/>);

    function allHandler()
    {
        setState(<ExchangeRateTable/>);
    }

    function historicalHandler()
    {
        setState(<HistoricalTable/>);
    }

    function dateHandler()
    {
        setState(<DailyTable/>);
    }

    return(
        <div>
            <div>
                <RadioGroup row defaultValue="All">
                    <FormControlLabel value="All" control={<Radio />} label="All" onClick={allHandler} />
                    <FormControlLabel value="Historical" control={<Radio />} label="Historical" onClick={historicalHandler}/>
                    <FormControlLabel value="Date" control={<Radio />} label="Date" onClick={dateHandler}/>
                </RadioGroup>
            </div>
            <div>
                {state}
            </div>
        </div>
    );
}