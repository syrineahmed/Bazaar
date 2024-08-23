import React from 'react';
import { DatePicker, Form } from 'antd';
import dayjs from 'dayjs';

const MyForm = () => {
    return (
        <Form>
            <Form.Item label="Date of Birth" name="dateOfBirth">
                <DatePicker
                    format="YYYY-MM-DD"
                    value={dayjs()} // Use dayjs() if not using Moment.js
                />
            </Form.Item>
        </Form>
    );
};

export default MyForm;
