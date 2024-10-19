import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import { useState, useEffect, useMemo } from 'react';
import { TablePagination } from '@mui/material';
import { properties } from '../../properties';

export default function ExchangeRateTable()
{
    /*const exchangeRate = {
        symbol: String,
        closePrice: Number,
        highestPrice: Number,
        lowestPrice: Number,
        numberOfTransaction: Number,
        openingPrice: Number,
        date: String,
        tradingVolume: Number,
        weightedAverage: Number
    }*/

    const [exchangeRates, setExchangeRetes] = useState([]);
    const [page, setPage] = useState(0);
    const [rowsPerPage, setRowsPerPage] = useState(10);

    useEffect(() => {myFetchFunction()}, []);

    const myFetchFunction = () =>
    {
        fetch(properties.DATA_STORER_EXCHANGE_RATE).then(
            (response) => 
            {
                if (!response.ok)
                {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            }
        ).then( (data) => 
            {
                console.log(data);
                setExchangeRetes([...exchangeRates, ...data]);
            }
        ).catch( err => console.error(err));
    }

    const handleChangePage = (event, newPage) =>
    {
        setPage(newPage);
    };

    const handleChangeRowsPerPage = (event) =>
    {
        setRowsPerPage(parseInt(event.target.value));
        setPage(0);
    };

    const visibleRows = useMemo( () =>
    {
        return (exchangeRates.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage));
    })

    const tableHead = () =>
    {
        return(
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
        );
    }

    const tableBody = () =>
    {/*
        return (exchangeRates.map((exchangeRate) => 
        {
            return(
                    <TableRow key={[exchangeRate.date, exchangeRate.symbol]}>
                            <TableCell>{exchangeRate.date}</TableCell>
                            <TableCell>{exchangeRate.symbol}</TableCell>
                            <TableCell>{exchangeRate.weightedAverage}</TableCell>
                            <TableCell>{exchangeRate.tradingVolume}</TableCell>
                            <TableCell>{exchangeRate.highestPrice}</TableCell>
                            <TableCell>{exchangeRate.lowestPrice}</TableCell>
                            <TableCell>{exchangeRate.openingPrice}</TableCell>
                            <TableCell>{exchangeRate.closePrice}</TableCell>
                        </TableRow>
            )
        }));*/
    }

    return (
        <div>
        <TableContainer component={Paper}>
        <Table size="small" sx={{ minWidth: 700 }}>
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
                {
                    visibleRows.map
                    (
                        (rate) => 
                        {
                            return (
                                <TableRow key={[rate.date, rate.symbol]}>
                                    <TableCell>{rate.date}</TableCell>
                                    <TableCell>{rate.symbol}</TableCell>
                                    <TableCell>{rate.weightedAverage}</TableCell>
                                    <TableCell>{rate.tradingVolume}</TableCell>
                                    <TableCell>{rate.highestPrice}</TableCell>
                                    <TableCell>{rate.lowestPrice}</TableCell>
                                    <TableCell>{rate.openingPrice}</TableCell>
                                    <TableCell>{rate.closePrice}</TableCell>
                                </TableRow>
                            )
                        }
                    )
                }
            </TableBody>
        </Table>
      </TableContainer>
      <TablePagination 
      rowsPerPageOptions={[10, 100, 1000]}
      count={exchangeRates.length}
      rowsPerPage={rowsPerPage}
      page={page}
      onRowsPerPageChange={handleChangeRowsPerPage} 
      onPageChange={handleChangePage}/>
      </div>
    );
}