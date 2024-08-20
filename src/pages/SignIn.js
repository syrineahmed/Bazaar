import React, { Component } from "react";
import { Link } from "react-router-dom";
import axios from "axios";
import { Layout, Menu, Button, Row, Col, Typography, Form, Input, Switch, message, notification } from "antd";
import signinbg from "../assets/images/img-signin.jpg";
import { DribbbleOutlined, TwitterOutlined, InstagramOutlined, GithubOutlined } from "@ant-design/icons";

function onChange(checked) {
  console.log(`switch to ${checked}`);
}

const { Title } = Typography;
const { Header, Footer, Content } = Layout;

export default class SignIn extends Component {
  onFinish = async (values) => {
    try {
      // Make the POST request to your Spring Boot backend
      const response = await axios.post("http://localhost:8080/api/v1/auth/signin", values);

      // Log the full response data to check its structure
      console.log("Response Data:", response.data);

      // Access the firstName from response.data.userDetails
      const firstName = response.data.userDetails?.firstName;

      if (firstName) {
        // Show notification with user's first name
        notification.success({
          message: 'Welcome',
          description: `Welcome, ${firstName}!`,
        });
      } else {
        console.warn("firstName is not available in the response data.");
        notification.warning({
          message: 'Welcome',
          description: "Welcome! (First name not available)",
        });
      }

      // Optionally, store the JWT token and refresh token
      localStorage.setItem("jwtToken", response.data.token);
      localStorage.setItem("refreshToken", response.data.refreshToken);

      // Redirect to profile page
      this.props.history.push("/profile");
    } catch (error) {
      console.error("Failed:", error.response ? error.response.data : error.message);
      message.error("Failed to sign in. Please check your credentials.");
    }
  };

  onFinishFailed = (errorInfo) => {
    console.log("Failed:", errorInfo);
  };

  render() {
    return (
        <>
          <Layout className="layout-default layout-signin">
            <Header>
              <div className="header-col header-brand">
                <h5>Muse Dashboard</h5>
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
                <Button type="primary">FREE DOWNLOAD</Button>
              </div>
            </Header>
            <Content className="signin">
              <Row gutter={[24, 0]} justify="space-around">
                <Col xs={{ span: 24, offset: 0 }} lg={{ span: 6, offset: 2 }} md={{ span: 12 }}>
                  <Title className="mb-15">Sign In</Title>
                  <Title className="font-regular text-muted" level={5}>
                    Enter your email and password to sign in
                  </Title>
                  <Form
                      onFinish={this.onFinish}
                      onFinishFailed={this.onFinishFailed}
                      layout="vertical"
                      className="row-col"
                  >
                    <Form.Item
                        className="username"
                        label="Email"
                        name="email"
                        rules={[
                          {
                            required: true,
                            message: "Please input your email!",
                          },
                        ]}
                    >
                      <Input placeholder="Email" />
                    </Form.Item>

                    <Form.Item
                        className="username"
                        label="Password"
                        name="password"
                        rules={[
                          {
                            required: true,
                            message: "Please input your password!",
                          },
                        ]}
                    >
                      <Input.Password placeholder="Password" />
                    </Form.Item>

                    <Form.Item
                        name="remember"
                        className="align-center"
                        valuePropName="checked"
                    >
                      <Switch defaultChecked onChange={onChange} />
                      Remember me
                    </Form.Item>

                    <Form.Item>
                      <Button
                          type="primary"
                          htmlType="submit"
                          style={{ width: "100%" }}
                      >
                        SIGN IN
                      </Button>
                    </Form.Item>
                    <p className="font-semibold text-muted">
                      Don't have an account?{" "}
                      <Link to="/sign-up" className="text-dark font-bold">
                        Sign Up
                      </Link>
                    </p>
                  </Form>
                </Col>
                <Col
                    className="sign-img"
                    style={{ padding: 12 }}
                    xs={{ span: 24 }}
                    lg={{ span: 12 }}
                    md={{ span: 12 }}
                >
                  <img src={signinbg} alt="" />
                </Col>
              </Row>
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
                  <Link to="#">{<GithubOutlined />}</Link>
                </Menu.Item>
              </Menu>
              <p className="copyright">
                {" "}
                Copyright © 2021 Muse by <a href="#pablo">Creative Tim</a>.{" "}
              </p>
            </Footer>
          </Layout>
        </>
    );
  }
}