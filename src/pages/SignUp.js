import React, { Component } from "react";
import { Layout, Menu, Button, Typography, Card, Form, Input, Checkbox, Select, notification, DatePicker } from "antd";
import logo1 from "../assets/images/logos-facebook.svg";
import logo2 from "../assets/images/logo-apple.svg";
import logo3 from "../assets/images/Google__G__Logo.svg.png";
import { Link } from "react-router-dom";
import { DribbbleOutlined, TwitterOutlined, InstagramOutlined, GithubOutlined } from "@ant-design/icons";
import authService from '../services/authService'; // Adjust the import path accordingly

const { Title } = Typography;
const { Header, Footer, Content } = Layout;

export default class SignUp extends Component {

    onFinish = async (values) => {
        try {
            // Adjust the request payload if necessary
            const response = await authService.signup({
                firstName: values.firstName,
                lastName: values.lastName,
                email: values.email,
                password: values.password,
                phoneNumber: values.phoneNumber,
                pictureUrl: values.pictureUrl,
                role: values.role,
                gender: values.gender,
                dateOfBirth: values.dateOfBirth // Ensure dateOfBirth is handled as a string
            });
            console.log('Success:', response.data);
            notification.success({
                message: 'Account Created',
                description: 'Your account has been successfully created.',
            });
            this.props.history.push('/sign-in');
        } catch (error) {
            if (error.response) {
                console.error('Response Error Data:', error.response.data);
                console.error('Response Status:', error.response.status);
            } else {
                console.error('Request Error:', error.message);
            }
        }
    };

    onFinishFailed = (errorInfo) => {
        console.log('Failed:', errorInfo);
    };

    render() {
        return (
            <>
                <div className="layout-default ant-layout layout-sign-up">
                    <Header>
                        <div className="header-col header-brand">
                            <h5>Bazaar</h5>
                        </div>
                        <div className="header-col header-nav">
                            <Menu mode="horizontal" defaultSelectedKeys={["1"]}>
                                <Menu.Item key="1">
                                    <Link to="/dashboard">
                                        <span> Dashboard</span>
                                    </Link>
                                </Menu.Item>
                                <Menu.Item key="2">
                                    <Link to="/profile">
                                        <span>Profile</span>
                                    </Link>
                                </Menu.Item>
                                <Menu.Item key="3">
                                    <Link to="/sign-up">
                                        <span> Sign Up</span>
                                    </Link>
                                </Menu.Item>
                                <Menu.Item key="4">
                                    <Link to="/sign-in">
                                        <span> Sign In</span>
                                    </Link>
                                </Menu.Item>
                            </Menu>
                        </div>
                        <div className="header-col header-btn">
                            <Button type="default">FREE DOWNLOAD</Button>
                        </div>
                    </Header>

                    <Content className="p-0">
                        <div className="sign-up-header">
                            <div className="content">
                                <Title>Sign Up</Title>
                                <p className="text-lg">
                                    Use these awesome forms to login or create new account in your project for free.
                                </p>
                            </div>
                        </div>

                        <Card
                            className="card-signup header-solid h-full ant-card pt-0"
                            title={<h5>Register With</h5>}
                            bordered={false}
                        >
                            <div className="sign-up-gateways">
                                <Button type="default">
                                    <img src={logo1} alt="logo 1" />
                                </Button>
                                <Button type="default">
                                    <img src={logo2} alt="logo 2" />
                                </Button>
                                <Button type="default">
                                    <img src={logo3} alt="logo 3" />
                                </Button>
                            </div>
                            <p className="text-center my-25 font-semibold text-muted">Or</p>
                            <Form
                                name="basic"
                                initialValues={{ remember: true }}
                                onFinish={this.onFinish}
                                onFinishFailed={this.onFinishFailed}
                                className="row-col"
                            >
                                <Form.Item
                                    name="firstName"
                                    rules={[{ required: true, message: "Please input your first name!" }]}
                                >
                                    <Input placeholder="First Name" />
                                </Form.Item>
                                <Form.Item
                                    name="lastName"
                                    rules={[{ required: true, message: "Please input your last name!" }]}
                                >
                                    <Input placeholder="Last Name" />
                                </Form.Item>
                                <Form.Item
                                    name="email"
                                    rules={[
                                        { required: true, message: "Please input your email!" },
                                        { type: 'email', message: "Please enter a valid email!" }
                                    ]}
                                >
                                    <Input placeholder="Email" />
                                </Form.Item>
                                <Form.Item
                                    name="password"
                                    rules={[{ required: true, message: "Please input your password!" }]}
                                >
                                    <Input.Password placeholder="Password" />
                                </Form.Item>
                                <Form.Item
                                    name="phoneNumber"
                                    rules={[{ required: true, message: "Please input your phone number!" }]}
                                >
                                    <Input placeholder="Phone Number" />
                                </Form.Item>
                                <Form.Item
                                    name="pictureUrl"
                                    rules={[{ required: true, message: "Please input your picture URL!" }]}
                                >
                                    <Input placeholder="Picture URL" />
                                </Form.Item>
                                <Form.Item
                                    name="role"
                                    rules={[{ required: true, message: "Please select your role!" }]}
                                >
                                    <Select placeholder="Select Role">
                                        <Select.Option value="USER">User</Select.Option>
                                        <Select.Option value="ADMIN">Admin</Select.Option>
                                        <Select.Option value="EMPLOYEE">Employee</Select.Option>
                                        <Select.Option value="COMPANY">Company</Select.Option>
                                    </Select>
                                </Form.Item>

                                <Form.Item
                                    name="gender"
                                    rules={[{ required: true, message: "Please select your gender!" }]}
                                >
                                    <Select placeholder="Select Gender">
                                        <Select.Option value="MALE">Male</Select.Option>
                                        <Select.Option value="FEMALE">Female</Select.Option>
                                        <Select.Option value="OTHER">Other</Select.Option>
                                    </Select>
                                </Form.Item>

                                <Form.Item
                                    name="dateOfBirth"
                                    label="Date of Birth"
                                    rules={[{ required: true, message: "Please input your date of birth!" }]}
                                >
                                    <DatePicker format="YYYY-MM-DD" />
                                </Form.Item>

                                <Form.Item name="remember" valuePropName="checked">
                                    <Checkbox>
                                        I agree to the{" "}
                                        <a href="#pablo" className="font-bold text-dark">
                                            Terms and Conditions
                                        </a>
                                    </Checkbox>
                                </Form.Item>
                                <Form.Item>
                                    <Button
                                        style={{ width: "100%" }}
                                        type="primary"
                                        htmlType="submit"
                                    >
                                        SIGN UP
                                    </Button>
                                </Form.Item>
                            </Form>
                            <p className="font-semibold text-muted text-center">
                                Already have an account?{" "}
                                <Link to="/sign-in" className="font-bold text-dark">
                                    Sign In
                                </Link>
                            </p>
                        </Card>
                    </Content>
                    <Footer>
                        <Menu mode="horizontal">
                            <Menu.Item>Company</Menu.Item>
                            <Menu.Item>About Us</Menu.Item>
                            <Menu.Item>Teams</Menu.Item>
                            <Menu.Item>Products</Menu.Item>
                            <Menu.Item>Blogs</Menu.Item>
                            <Menu.Item>Pricing</Menu.Item>
                        </Menu>
                        <Menu mode="horizontal" className="menu-nav-social">
                            <Menu.Item>
                                <Link to="#">{<DribbbleOutlined />}</Link>
                            </Menu.Item>
                            <Menu.Item>
                                <Link to="#">{<TwitterOutlined />}</Link>
                            </Menu.Item>
                            <Menu.Item>
                                <Link to="#">{<InstagramOutlined />}</Link>
                            </Menu.Item>
                            <Menu.Item>
                                <Link to="#">
                                    <svg
                                        viewBox="0 0 24 24"
                                        width="24"
                                        height="24"
                                        stroke="currentColor"
                                        strokeWidth="2"
                                        fill="none"
                                        strokeLinecap="round"
                                        strokeLinejoin="round"
                                        className="feather feather-github"
                                    >
                                        <path d="M9 19c-5 1.5-5-2.5-7-3m14 6v-3.87a3.37 3.37 0 0 0-.94-2.61c3.14-.35 6.44-1.54 6.44-7A5.44 5.44 0 0 0 20 4.77 5.07 5.07 0 0 0 19.91 1S18.73.65 16 2.48a13.38 13.38 0 0 0-7 0C6.27.65 5.09 1 5.09 1A5.07 5.07 0 0 0 5 4.77 5.44 5.44 0 0 0 3.5 8.5c0 5.42 3.3 6.61 6.44 7a3.37 3.37 0 0 0-.94 2.61V22"></path>
                                    </svg>
                                </Link>
                            </Menu.Item>
                            <Menu.Item>
                                <Link to="#">{<GithubOutlined />}</Link>
                            </Menu.Item>
                        </Menu>
                        <p className="text-center text-muted">
                            &copy; {new Date().getFullYear()} Your Company. All Rights Reserved.
                        </p>
                    </Footer>
                </div>
            </>
        );
    }
}