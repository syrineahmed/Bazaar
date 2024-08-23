import dayjs from 'dayjs';
import { DatePicker } from 'antd';
import 'antd/dist/antd.css'; // or 'antd/dist/antd.less'

const MyComponent = () => (
    <DatePicker
        format="YYYY-MM-DD"
        value={dayjs()}
        onChange={(date) => console.log(date)}
    />
);

export default MyComponent;
