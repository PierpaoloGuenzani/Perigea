import { useRef, useState } from "react";
import { TextField, Button, Paper, Table, TableHead, TableBody, TableContainer, TableRow, TableCell } from "@mui/material";
import { properties } from '../../properties';


export default function DailyTable()
{
  const dateRef = useRef();
  const [exchangeRates, setExchangeRates] = useState([]);

  const fetchData = () => {
    setExchangeRates([]);
        fetch(properties.DATA_STORER_EXCHANGE_RATE+dateRef.current.value)
          .then((response) => response.json())
          .then((data) => 
          {
            console.log(data);
            setExchangeRates([...exchangeRates, ...data]);
          })
          .catch(err => console.log(err));
      };

    return (
        <div className="daily-table">
          <div className="daily-form">
            <TextField size='small' label="Date" inputRef={dateRef}></TextField>
            <Button variant='outlined' onClick={fetchData}>Search</Button>
          </div>
        <TableContainer component={Paper}>
        <Table sx={{ minWidth: 700 }}  size='small'>
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
                <TableRow key={[row.date, row.symbol]} sx={{ '&:last-child td, &:last-child th': { border: 0 } }}>
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