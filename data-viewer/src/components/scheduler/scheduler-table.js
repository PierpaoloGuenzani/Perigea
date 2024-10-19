import { Table, TableBody, TableCell, TableContainer, TableHead, TablePagination, TableRow } from "@mui/material";
import { useState, useEffect, useContext } from "react";
import { TaskContext } from "./scheduler-view";
import { properties } from "../../properties";

export default function SchedulerTable()
{
    const [tasks, setTasks] = useState([]);
    const [page, setPage] = useState(0);
    const [rowsPerPage, setRowsPerPage] = useState(10);
    const {task, setTask, setModifyState} = useContext(TaskContext);

    /*setTasks({
        name: "paolo",
        schedulingInterval: "asdasd",
        type: "daily",
        state: "active"
    });*/
    
    useEffect(() => myFetchFunction(), []);

    function myFetchFunction()
    {
        fetch(properties.DATA_IMPORTER_SCHEDULER_URI).then
        (
            (response) => 
            {
                if(!response.ok)
                {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            }
        ).then
        (
            (data) =>
            {
                console.log(data);
                setTasks([...tasks, ...data]);
                /*data.forEach(element => {
                    console.log(element);
                    setTasks([...tasks, element]);
                });*/
                //setTasks([...data]); //shallw copy
                //newData = data.map(a => ({...a}));
                //console.log(newData);
                //console.log(tasks);
            }
        )
        .catch(err => console.error(err));
    }

    const handleClick = (event, row) =>
    {
        setTask(row);
        setModifyState(true);
        console.log("clicked on: "+task.taskName);
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
    
    return (
        <TableContainer>
            <Table>
                <TableHead>
                    <TableRow>
                        <TableCell>Name</TableCell>
                        <TableCell>Scheduling Interval</TableCell>
                        <TableCell>Scheduling Type</TableCell>
                        <TableCell>Scheduling State</TableCell>
                        <TableCell>Initialization Time</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {
                        tasks.map( (row) => 
                        {
                            return(
                            <TableRow onClick={ (event) => handleClick(event, row)} key={row.taskName}>
                                <TableCell>{row.taskName}</TableCell>
                                <TableCell>{row.schedulingInterval}</TableCell>
                                <TableCell>{row.type}</TableCell>
                                <TableCell>{row.state}</TableCell>
                                <TableCell>{row.initializationTime}</TableCell>
                            </TableRow>);
                        })
                    }
                </TableBody>
            </Table>
            <TablePagination
            count={tasks.length}
            onPageChange={handleChangePage}
            onRowsPerPageChange={handleChangeRowsPerPage}
            page={page}
            rowsPerPage={rowsPerPage}
            />
        </TableContainer>
    );
}