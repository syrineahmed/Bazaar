import React, { useEffect, useState } from "react";
import { Row, Col, Card, Radio, Table, Upload, message, Button, Avatar, Typography, Progress } from "antd";
import { ToTopOutlined } from "@ant-design/icons";
import ava1 from "../assets/images/logo-shopify.svg";
import ava2 from "../assets/images/logo-atlassian.svg";
import ava3 from "../assets/images/logo-slack.svg";
import ava5 from "../assets/images/logo-jira.svg";
import ava6 from "../assets/images/logo-invision.svg";
import pencil from "../assets/images/pencil.svg";
import { Link } from "react-router-dom";
import axios from "axios";

const { Title } = Typography;

const formProps = {
  name: "file",
  action: "https://www.mocky.io/v2/5cc8019d300000980a055e76",
  headers: {
    authorization: "authorization-text",
  },
  onChange(info) {
    if (info.file.status !== "uploading") {
      console.log(info.file, info.fileList);
    }
    if (info.file.status === "done") {
      message.success(`${info.file.name} file uploaded successfully`);
    } else if (info.file.status === "error") {
      message.error(`${info.file.name} file upload failed.`);
    }
  },
};

// Static data for the project table
const projectColumns = [
  {
    title: "COMPANIES",
    dataIndex: "name",
    width: "32%",
  },
  {
    title: "BUDGET",
    dataIndex: "age",
  },
  {
    title: "STATUS",
    dataIndex: "address",
  },
  {
    title: "COMPLETION",
    dataIndex: "completion",
  },
];

const projectData = [
  {
    key: "1",
    name: (
        <>
          <Avatar.Group>
            <Avatar className="shape-avatar" src={ava1} size={25} alt="" />
            <div className="avatar-info">
              <Title level={5}>Spotify Version</Title>
            </div>
          </Avatar.Group>
        </>
    ),
    age: (
        <>
          <div className="semibold">$14,000</div>
        </>
    ),
    address: (
        <>
          <div className="text-sm">working</div>
        </>
    ),
    completion: (
        <>
          <div className="ant-progress-project">
            <Progress percent={30} size="small" />
            <span>
            <Link to="/">
              <img src={pencil} alt="" />
            </Link>
          </span>
          </div>
        </>
    ),
  },
  // Add other static data rows here as needed
];

const Tables = () => {
  const [users, setUsers] = useState([]);

  useEffect(() => {
    // Fetch users data
    axios
        .get("http://localhost:8080/api/v1/admin/all") // Replace with your backend URL
        .then((response) => {
          setUsers(response.data);
        })
        .catch((error) => {
          console.error("Error fetching users:", error);
        });
  }, []);

  const handleDelete = (id) => {
    axios
        .delete(`http://localhost:8080/api/v1/admin/users/${id}`)
        .then(() => {
          message.success("User deleted successfully");
          setUsers(users.filter((user) => user.id !== id));
        })
        .catch((error) => {
          // Log or display detailed error message
          console.error("Error deleting user:", error.response ? error.response.data : error.message);
          message.error("Error deleting user: " + (error.response ? error.response.data.message : error.message));
        });
  };

  const userColumns = [
    {
      title: "IMAGE",
      dataIndex: "image",
      key: "image",
      render: (imageData) => (
          <Avatar
              src={`data:image/jpeg;base64,${imageData}`}
              alt="User Image"
              size={40}
          />
      ),
    },
    {
      title: "EMAIL",
      dataIndex: "email",
      key: "email",
    },
    {
      title: "PHONE NUMBER",
      dataIndex: "phoneNumber",
      key: "phoneNumber",
    },
    {
      title: "DATE OF BIRTH",
      dataIndex: "dateOfBirth",
      key: "dateOfBirth",
    },
    {
      title: "ACTION",
      key: "action",
      render: (_, record) => (
          <Button type="primary" danger onClick={() => handleDelete(record.id)}>
            Delete
          </Button>
      ),
    },
  ];

  const userData = users.map((user, index) => ({
    key: index,
    id: user.id,
    image: user.image,
    email: user.email,
    phoneNumber: user.phoneNumber,
    dateOfBirth: new Date(user.dateOfBirth).toLocaleDateString(), // Convert to readable date format
  }));

  return (
      <>
        <div className="tabled">
          <Row gutter={[24, 0]}>
            <Col xs="24" xl={24}>
              <Card
                  bordered={false}
                  className="criclebox tablespace mb-24"
                  title="Users Table"
              >
                <div className="table-responsive">
                  <Table
                      columns={userColumns}
                      dataSource={userData}
                      pagination={false}
                      className="ant-border-space"
                  />
                </div>
              </Card>
            </Col>

            {/* Projects Table */}
            <Col xs="24" xl={24}>
              <Card
                  bordered={false}
                  className="criclebox tablespace mb-24"
                  title="Projects Table"
                  extra={
                    <>
                      <Radio.Group onChange={(e) => console.log(e.target.value)} defaultValue="all">
                        <Radio.Button value="all">All</Radio.Button>
                        <Radio.Button value="online">ONLINE</Radio.Button>
                        <Radio.Button value="store">STORES</Radio.Button>
                      </Radio.Group>
                    </>
                  }
              >
                <div className="table-responsive">
                  <Table
                      columns={projectColumns}
                      dataSource={projectData}
                      pagination={false}
                      className="ant-border-space"
                  />
                </div>
                <div className="uploadfile pb-15 shadow-none">
                  <Upload {...formProps}>
                    <Button
                        type="dashed"
                        className="ant-full-box"
                        icon={<ToTopOutlined />}
                    >
                      Click to Upload
                    </Button>
                  </Upload>
                </div>
              </Card>
            </Col>
          </Row>
        </div>
      </>
  );
};

export default Tables;
