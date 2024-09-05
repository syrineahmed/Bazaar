import React, { useEffect, useState } from "react";
import { Row, Col, Card, Radio, Table, Upload, message, Button, Avatar, Typography, Progress, Modal, Select, Input, Form } from "antd";
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
    const [categories, setCategories] = useState([]);
    const [isModalVisible, setIsModalVisible] = useState(false);
    const [selectedUser, setSelectedUser] = useState(null);
    const [newRole, setNewRole] = useState("");
    const [isCategoryModalVisible, setIsCategoryModalVisible] = useState(false);
    const [newCategory, setNewCategory] = useState({ name: "", description: "" });
    const [isUpdateCategoryModalVisible, setIsUpdateCategoryModalVisible] = useState(false);
    const [selectedCategory, setSelectedCategory] = useState(null);
    const [isDeleteConfirmVisible, setIsDeleteConfirmVisible] = useState(false);
    const [categoryToDelete, setCategoryToDelete] = useState(null);
    const [isUserDeleteConfirmVisible, setIsUserDeleteConfirmVisible] = useState(false);
    const [userToDelete, setUserToDelete] = useState(null);

    useEffect(() => {
        fetchUsers();
        fetchCategories();
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

    const fetchCategories = () => {
        axios
            .get(`http://localhost:8080/api/v1/admin/category/all`)
            .then((response) => {
                setCategories(response.data);
            })
            .catch((error) => {
                console.error("Error fetching categories:", error);
            });
    };

    const handleDelete = (id) => {
        setUserToDelete(id);
        setIsUserDeleteConfirmVisible(true);
    };

    const confirmDeleteUser = () => {
        axios
            .delete(`http://localhost:8080/api/v1/admin/users/${userToDelete}`)
            .then(() => {
                message.success("User deleted successfully");
                setUsers(users.filter((user) => user.id !== userToDelete));
                setIsUserDeleteConfirmVisible(false);
                setUserToDelete(null);
            })
            .catch((error) => {
                console.error("Error deleting user:", error.response ? error.response.data : error.message);
                message.error("Error deleting user: " + (error.response ? error.response.data.message : error.message));
                setIsUserDeleteConfirmVisible(false);
                setUserToDelete(null);
            });
    };

    const handleDeleteCategory = (id) => {
        setCategoryToDelete(id);
        setIsDeleteConfirmVisible(true);
    };

    const confirmDeleteCategory = () => {
        axios
            .delete(`http://localhost:8080/api/v1/admin/category/delete/${categoryToDelete}`)
            .then(() => {
                message.success("Category deleted successfully");
                setCategories(categories.filter((category) => category.id !== categoryToDelete));
                setIsDeleteConfirmVisible(false);
                setCategoryToDelete(null);
            })
            .catch((error) => {
                console.error("Error deleting category:", error.response ? error.response.data : error.message);
                message.error("Error deleting category: " + (error.response ? error.response.data.message : error.message));
                setIsDeleteConfirmVisible(false);
                setCategoryToDelete(null);
            });
    };

    const handleUpdateRole = (email) => {
        setSelectedUser(email);
        setIsModalVisible(true);
    };

    const handleUpdateCategory = (category) => {
        setSelectedCategory(category);
        setIsUpdateCategoryModalVisible(true);
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

    const handleUpdateCategoryOk = () => {
        const payload = {
            id: selectedCategory.id,
            name: selectedCategory.name,
            description: selectedCategory.description
        };

        axios
            .put(`http://localhost:8080/api/v1/admin/category/update/${selectedCategory.id}`, payload, {
                headers: {
                    'Content-Type': 'application/json',
                },
            })
            .then(() => {
                message.success("Category updated successfully");
                setCategories(categories.map(category => category.id === selectedCategory.id ? selectedCategory : category));
                setIsUpdateCategoryModalVisible(false);
                setSelectedCategory(null);
            })
            .catch((error) => {
                console.error("Error updating category:", error);
                message.error("Error updating category: " + (error.response ? error.response.data.message : error.message));
            });
    };

    const handleCancel = () => {
        setIsModalVisible(false);
        setNewRole("");
    };

    const handleUpdateCategoryCancel = () => {
        setIsUpdateCategoryModalVisible(false);
        setSelectedCategory(null);
    };

    const handleSearchChange = (e) => {
        fetchUsers(e.target.value);
    };

    const handleCategoryCreate = () => {
        axios
            .post(`http://localhost:8080/api/v1/admin/category/create`, newCategory)
            .then((response) => {
                message.success("Category created successfully");
                setCategories([...categories, response.data]);
                setIsCategoryModalVisible(false);
                setNewCategory({ name: "", description: "" });
            })
            .catch((error) => {
                console.error("Error creating category:", error);
                message.error("Error creating category: " + (error.response ? error.response.data.message : error.message));
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
                        🗑️
                    </Button>
                    <Button type="default" onClick={() => handleUpdateRole(record.email)}>
                        ✏️
                    </Button>
                </>
            ),
        },
    ];

    const categoryColumns = [
        {
            title: "NAME",
            dataIndex: "name",
            key: "name",
        },
        {
            title: "DESCRIPTION",
            dataIndex: "description",
            key: "description",
        },
        {
            title: "ACTION",
            key: "action",
            render: (_, record) => (
                <>
                    <Button type="primary" danger onClick={() => handleDeleteCategory(record.id)}>
                        🗑️
                    </Button>
                    <Button type="default" onClick={() => handleUpdateCategory(record)}>
                        ✏️
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

    const categoryData = categories.map((category, index) => ({
        key: index,
        id: category.id,
        name: category.name,
        description: category.description,
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
                                <Search
                                    placeholder="Search users"
                                    onChange={handleSearchChange}
                                    style={{ width: 200, margin: '0 auto', display: 'block' }}
                                />
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

                    {/* Categories Table */}
                    <Col xs="24" xl={24}>
                        <Card
                            bordered={false}
                            className="criclebox tablespace mb-24"
                            title="Categories Table"
                            extra={
                                <Button type="primary" onClick={() => setIsCategoryModalVisible(true)}>
                                    Add Category
                                </Button>
                            }
                        >
                            <div className="table-responsive">
                                <Table
                                    columns={categoryColumns}
                                    dataSource={categoryData}
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
            <Modal title="Create Category" visible={isCategoryModalVisible} onOk={handleCategoryCreate} onCancel={() => setIsCategoryModalVisible(false)}>
                <Form layout="vertical">
                    <Form.Item label="Name">
                        <Input value={newCategory.name} onChange={(e) => setNewCategory({ ...newCategory, name: e.target.value })} />
                    </Form.Item>
                    <Form.Item label="Description">
                        <Input value={newCategory.description} onChange={(e) => setNewCategory({ ...newCategory, description: e.target.value })} />
                    </Form.Item>
                </Form>
            </Modal>
            <Modal title="Update Category" visible={isUpdateCategoryModalVisible} onOk={handleUpdateCategoryOk} onCancel={handleUpdateCategoryCancel}>
                <Form layout="vertical">
                    <Form.Item label="Name">
                        <Input value={selectedCategory?.name} onChange={(e) => setSelectedCategory({ ...selectedCategory, name: e.target.value })} />
                    </Form.Item>
                    <Form.Item label="Description">
                        <Input value={selectedCategory?.description} onChange={(e) => setSelectedCategory({ ...selectedCategory, description: e.target.value })} />
                    </Form.Item>
                </Form>
            </Modal>
            <Modal title="Confirm Deletion" visible={isDeleteConfirmVisible} onOk={confirmDeleteCategory} onCancel={() => setIsDeleteConfirmVisible(false)}>
                <p>Voulez-vous confirmer la suppression ?</p>
            </Modal>
            <Modal title="Confirm User Deletion" visible={isUserDeleteConfirmVisible} onOk={confirmDeleteUser} onCancel={() => setIsUserDeleteConfirmVisible(false)}>
                <p>Voulez-vous confirmer la suppression de l'utilisateur ?</p>
            </Modal>
        </>
    );
};

export default Tables;