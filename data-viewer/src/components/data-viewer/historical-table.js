import PropTypes from 'prop-types';
import { useState, useEffect, useRef } from "react";
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow, TextField, Button } from "@mui/material";
import { properties } from '../../properties';

/*HistoricalTable.prototype = {
    symbol: PropTypes.string.isRequired,
    startingDate: PropTypes.string,
    endingDate: PropTypes.string
}*/

export default function HistoricalTable()
{
    const symbolRef = useRef();
    const [exchangeRates, setExchangeRates] = useState([]);

    function myFetchFunction()
    {
        setExchangeRates([]);
        fetch(properties.DATA_STORER_HISTORICAL+symbolRef.current.value)
        .then(response => response.json())
        .then(data =>
            {
                console.log(data);
                setExchangeRates([...exchangeRates,...data])
            }
        );
    }

    //myFetchFunction();

    return (
        <div className="hystorical-table">
            <div className="daily-form">
                <TextField size='small' label="Symbol" inputRef={symbolRef}></TextField>
                <Button variant='outlined' onClick={myFetchFunction}>Search</Button>
            </div>
        <TableContainer>
        <Table sx={{ minWidth: 700 }} size='small'>
            <TableHead>
                <TableRow>
                    <TableCell>Date</TableCell>
                    <TableCell>Symbol</TableCell>
                    <TableCell>Weighted Average</TableCell>
                    <TableCell>Traiding Volume</TableCell>
                    <TableCell>Highest Price</TableCell>
                    <TableCell>Lowest Price</TableCell>
                    <TableCell>Opening Price</TableCell>
                    <TableCell>Closing Price</TableCell>
                </TableRow>
            </TableHead>
            <TableBody>
            {exchangeRates.map((row) => (
                <TableRow key={[row.date]} sx={{ '&:last-child td, &:last-child th': { border: 0 } }}>
                    <TableCell>{row.date}</TableCell>
                    <TableCell>{row.symbol}</TableCell>
                    <TableCell>{row.weightedAverage}</TableCell>
                    <TableCell>{row.tradingVolume}</TableCell>
                    <TableCell>{row.highestPrice}</TableCell>
                    <TableCell>{row.lowestPrice}</TableCell>
                    <TableCell>{row.openingPrice}</TableCell>
                    <TableCell>{row.closePrice}</TableCell>
                </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
      </div>
    );
}