import React, { useState, useEffect } from "react";
import { Form, Input, Button, message } from "antd";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const ProfileEditForm = ({ userData }) => {
    const [form] = Form.useForm();
    const navigate = useNavigate();

    useEffect(() => {
        if (userData) {
            form.setFieldsValue(userData);
        }
    }, [userData, form]);

    const onFinish = async (values) => {
        try {
            const token = localStorage.getItem("jwtToken");
            const response = await axios.put(
                "http://localhost:8080/api/v1/user/updateprofile",
                values,
                {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                }
            );
            message.success("Profile updated successfully!");
            navigate("/profile"); // Redirect back to the profile page
        } catch (error) {
            console.error("Failed to update profile:", error);
            message.error("Failed to update profile. Please try again.");
        }
    };

    return (
        <Form
            form={form}
            layout="vertical"
            onFinish={onFinish}
            initialValues={userData}
        >
            <Form.Item
                name="firstName"
                label="First Name"
                rules={[{ required: true, message: "Please enter your first name" }]}
            >
                <Input />
            </Form.Item>
            <Form.Item
                name="lastName"
                label="Last Name"
                rules={[{ required: true, message: "Please enter your last name" }]}
            >
                <Input />
            </Form.Item>
            <Form.Item
                name="email"
                label="Email"
                rules={[{ required: true, message: "Please enter your email" }]}
            >
                <Input />
            </Form.Item>
            <Form.Item
                name="phoneNumber"
                label="Phone Number"
                rules={[{ required: true, message: "Please enter your phone number" }]}
            >
                <Input />
            </Form.Item>
            <Form.Item
                name="dateOfBirth"
                label="Date of Birth"
                rules={[{ required: true, message: "Please enter your date of birth" }]}
            >
                <Input />
            </Form.Item>
            <Form.Item
                name="pictureUrl"
                label="Profile Picture URL"
            >
                <Input />
            </Form.Item>
            <Form.Item>
                <Button type="primary" htmlType="submit">
                    Save Changes
                </Button>
                <Button
                    style={{ marginLeft: "10px" }}
                    onClick={() => navigate("/profile")}
                >
                    Cancel
                </Button>
            </Form.Item>
        </Form>
    );
};

export default ProfileEditForm;
