import React, { useEffect, useState } from "react";
import { Row, Col, Card, Radio, Table, Upload, message, Button, Avatar, Typography, Progress, Modal, Select, Input } from "antd";
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
const { Option } = Select;
const { Search } = Input;

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
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [selectedUser, setSelectedUser] = useState(null);
  const [newRole, setNewRole] = useState("");

  useEffect(() => {
    fetchUsers();
  }, []);

  const fetchUsers = (searchTerm = "") => {
    axios
        .get(`http://localhost:8080/api/v1/admin/users/search`, {
          params: { searchTerm }
        })
        .then((response) => {
          setUsers(response.data);
        })
        .catch((error) => {
          console.error("Error fetching users:", error);
        });
  };

  const handleDelete = (id) => {
    axios
        .delete(`http://localhost:8080/api/v1/admin/users/${id}`)
        .then(() => {
          message.success("User deleted successfully");
          setUsers(users.filter((user) => user.id !== id));
        })
        .catch((error) => {
          console.error("Error deleting user:", error.response ? error.response.data : error.message);
          message.error("Error deleting user: " + (error.response ? error.response.data.message : error.message));
        });
  };

  const handleUpdateRole = (email) => {
    setSelectedUser(email);
    setIsModalVisible(true);
  };

  const handleOk = () => {
    axios
        .put(`http://localhost:8080/api/v1/admin/users/${selectedUser}/role`, null, {
          params: { newRole }
        })
        .then(() => {
          message.success("User role updated successfully");
          setUsers(users.map(user => user.email === selectedUser ? { ...user, role: newRole } : user));
          setIsModalVisible(false);
          setNewRole("");
        })
        .catch((error) => {
          console.error("Error updating user role:", error);
          message.error("Error updating user role: " + (error.response ? error.response.data.message : error.message));
        });
  };

  const handleCancel = () => {
    setIsModalVisible(false);
    setNewRole("");
  };

  const handleSearchChange = (e) => {
    fetchUsers(e.target.value);
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
      title: "ROLE",
      dataIndex: "role",
      key: "role",
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
          <>
            <Button type="primary" danger onClick={() => handleDelete(record.id)}>
              Delete
            </Button>
            <Button type="default" onClick={() => handleUpdateRole(record.email)}>
              Update Role
            </Button>
          </>
      ),
    },
  ];

  const userData = users.map((user, index) => ({
    key: index,
    id: user.id,
    image: user.image,
    email: user.email,
    role: user.role,
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
                  extra={
                    <div style={{ display: 'flex', justifyContent: 'center' }}>
                      <Search
                          placeholder="Search users"
                          onChange={handleSearchChange}
                          style={{ width: 200 }}
                      />
                    </div>
                  }
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
        <Modal title="Update User Role" visible={isModalVisible} onOk={handleOk} onCancel={handleCancel}>
          <Select placeholder="Select new role" value={newRole} onChange={(value) => setNewRole(value)} style={{ width: '100%' }}>
            <Option value="ADMIN">ADMIN</Option>
            <Option value="USER">USER</Option>
            <Option value="COMPANY">COMPANY</Option>
            <Option value="EMPLOYEE">EMPLOYEE</Option>
          </Select>
        </Modal>
      </>
  );
};

export default Tables;