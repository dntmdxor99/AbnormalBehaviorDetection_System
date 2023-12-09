import DatePicker from "react-datepicker";
import { ko } from "date-fns/esm/locale";
import { useState, useEffect } from "react";

const moment = require("moment");
const SelectDate = ({ handleDate }) => {
  const [dateRange, setDateRange] = useState([null, null]);

  useEffect(() => {
    if (dateRange[0] !== null && dateRange[1] !== null) {
      handleDate([
        moment(dateRange[0]).format("YYYY-MM-DDTHH:mm:ss"),
        moment(dateRange[1]).format("YYYY-MM-DDTHH:mm:ss"),
      ]);
    }
  }, [moment, dateRange]);

  //   useEffect(() => {
  //     const [startDate, endDate] = dateRange;
  //     if (startDate && endDate) {
  //       setFormattedStartDate(moment(startDate).format("YYYY-MM-DDTHH:mm:ss"));
  //       setFormattedEndDate();
  //     } else {
  //       setFormattedStartDate(null);
  //       setFormattedEndDate(null);
  //     }
  //   }, [moment, dateRange]);

  return (
    <div>
      <DatePicker
        selectsRange={true}
        className="datepicker"
        locale={ko}
        dateFormat="yyyy년 MM월 dd일"
        selected={dateRange[0]}
        startDate={dateRange[0]}
        endDate={dateRange[1]}
        maxDate={new Date()}
        onChange={(setChangeDate) => {
          setDateRange(setChangeDate);
        }}
        isClearable={true}
      />
    </div>
  );
};

export default SelectDate;
