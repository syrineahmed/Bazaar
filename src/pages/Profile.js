import React, { useEffect, useState } from "react";
import axios from "axios";
import {
  Row,
  Col,
  Card,
  Avatar,
  Descriptions,
  message,
  Button,
  List,
  Radio,
  Switch,
  Upload,
  Modal,
  Form,
  Input,
  DatePicker,
} from "antd";
import {
  FacebookOutlined,
  TwitterOutlined,
  InstagramOutlined,
  VerticalAlignTopOutlined,
} from "@ant-design/icons";
import BgProfile from "../assets/images/bg-profile.jpg";
import profilavatar from "../assets/images/face-1.jpg";
import convesionImg from "../assets/images/face-3.jpg";
import convesionImg2 from "../assets/images/face-4.jpg";
import convesionImg3 from "../assets/images/face-5.jpeg";
import convesionImg4 from "../assets/images/face-6.jpeg";
import convesionImg5 from "../assets/images/face-2.jpg";
import project1 from "../assets/images/home-decor-1.jpeg";
import project2 from "../assets/images/home-decor-2.jpeg";
import project3 from "../assets/images/home-decor-3.jpeg";
import moment from "moment";

function Profile() {
  const [userData, setUserData] = useState(null);
  const [imageURL, setImageURL] = useState(false);
  const [, setLoading] = useState(false);
  const [isModalVisible, setIsModalVisible] = useState(false);

  useEffect(() => {
    const fetchProfile = async () => {
      try {
        const token = localStorage.getItem("jwtToken");
        const response = await axios.get(
            "http://localhost:8080/api/v1/user/profile",
            {
              headers: {
                Authorization: `Bearer ${token}`,
                "Content-Type": "application/json",
              },
              responseType: 'json', // Fetch as JSON
            }
        );

        const { image, ...userData } = response.data;

        // Convert BLOB image to Base64
        const base64Image = `data:image/jpeg;base64,${image}`;

        setUserData({ ...userData, pictureUrl: base64Image });
      } catch (error) {
        console.error("Failed to fetch user profile:", error);
        message.error("Failed to fetch user profile. Please try again.");
      }
    };

    fetchProfile();
  }, []);

  const getBase64 = (img, callback) => {
    const reader = new FileReader();
    reader.addEventListener("load", () => callback(reader.result));
    reader.readAsDataURL(img);
  };

  const beforeUpload = (file) => {
    const isJpgOrPng = file.type === "image/jpeg" || file.type === "image/png";
    if (!isJpgOrPng) {
      message.error("You can only upload JPG/PNG file!");
    }
    const isLt2M = file.size / 1024 / 1024 < 2;
    if (!isLt2M) {
      message.error("Image must smaller than 2MB!");
    }
    return isJpgOrPng && isLt2M;
  };

  const handleChange = (info) => {
    if (info.file.status === "uploading") {
      setLoading(true);
      return;
    }
    if (info.file.status === "done") {
      getBase64(info.file.originFileObj, (imageUrl) => {
        setLoading(false);
        setImageURL(imageUrl);
      });
    }
  };

  const handlePencilClick = () => {
    setIsModalVisible(true);
  };

  const handleCancel = () => {
    setIsModalVisible(false);
  };

  const handleFormSubmit = async (values) => {
    try {
      const formData = new FormData();
      formData.append("user", JSON.stringify({
        id: userData?.id,
        firstName: values.firstName,
        lastName: values.lastName,
        email: values.email,
        phoneNumber: values.phoneNumber,
        dateOfBirth: values.dateOfBirth.format("YYYY-MM-DD"),
      }));

      if (values.img && values.img[0].originFileObj) {
        formData.append("img", values.img[0].originFileObj);
      }

      const token = localStorage.getItem("jwtToken");
      const response = await axios.put(
          "http://localhost:8080/api/v1/user/updateprofile",
          formData,
          {
            headers: {
              Authorization: `Bearer ${token}`,
              "Content-Type": "multipart/form-data",
            },
          }
      );

      if (response.status === 200) {
        message.success("User updated successfully");

        // Update the imageURL state if a new image was uploaded
        if (values.img && values.img[0].originFileObj) {
          getBase64(values.img[0].originFileObj, (imageUrl) => {
            setImageURL(imageUrl);
            setUserData({
              ...userData,
              firstName: values.firstName,
              lastName: values.lastName,
              email: values.email,
              phoneNumber: values.phoneNumber,
              dateOfBirth: values.dateOfBirth.format("YYYY-MM-DD"),
              pictureUrl: imageUrl,
            });
          });
        } else {
          setUserData({
            ...userData,
            firstName: values.firstName,
            lastName: values.lastName,
            email: values.email,
            phoneNumber: values.phoneNumber,
            dateOfBirth: values.dateOfBirth.format("YYYY-MM-DD"),
            pictureUrl: userData.pictureUrl,
          });
        }

        setIsModalVisible(false);
      } else {
        message.error("Failed to update user");
      }
    } catch (error) {
      console.error("Failed to update user profile:", error);
      if (error.response && error.response.data) {
        message.error(`Failed to update user profile: ${error.response.data}`);
      } else {
        message.error("Failed to update user profile. Please try again.");
      }
    }
  };

  const pencil = (
      <svg
          width="20"
          height="20"
          viewBox="0 0 20 20"
          fill="none"
          xmlns="http://www.w3.org/2000/svg"
          key={0}
      >
        <path
            d="M13.5858 3.58579C14.3668 2.80474 15.6332 2.80474 16.4142 3.58579C17.1953 4.36683 17.1953 5.63316 16.4142 6.41421L15.6213 7.20711L12.7929 4.37868L13.5858 3.58579Z"
            className="fill-gray-7"
        ></path>
        <path
            d="M11.3787 5.79289L3 14.1716V17H5.82842L14.2071 8.62132L11.3787 5.79289Z"
            className="fill-gray-7"
        ></path>
      </svg>
  );

  const uploadButton = (
      <div className="ant-upload-text font-semibold text-dark">
        {<VerticalAlignTopOutlined style={{ width: 20, color: "#000" }} />}
        <div>Upload New Project</div>
      </div>
  );

  const data = [
    {
      title: "Sophie B.",
      avatar: convesionImg,
      description: "Hi! I need more information…",
    },
    {
      title: "Anne Marie",
      avatar: convesionImg2,
      description: "Awesome work, can you…",
    },
    {
      title: "Ivan",
      avatar: convesionImg3,
      description: "About files I can…",
    },
    {
      title: "Peterson",
      avatar: convesionImg4,
      description: "Have a great afternoon…",
    },
    {
      title: "Nick Daniel",
      avatar: convesionImg5,
      description: "Hi! I need more information…",
    },
  ];

  const project = [
    {
      img: project1,
      titlesub: "Project #1",
      title: "Modern",
      disciption:
          "As Uber works through a huge amount of internal management turmoil.",
    },
    {
      img: project2,
      titlesub: "Project #2",
      title: "Scandinavian",
      disciption:
          "Music is something that every person has his or her own specific opinion about.",
    },
    {
      img: project3,
      titlesub: "Project #3",
      title: "Minimalist",
      disciption:
          "Different people have different taste, and various types of music, Zimbali Resort",
    },
  ];

  const [isPasswordModalVisible, setIsPasswordModalVisible] = useState(false);

  const handlePasswordClick = () => {
    setIsPasswordModalVisible(true);
  };

  const handlePasswordCancel = () => {
    setIsPasswordModalVisible(false);
  };
  const handlePasswordChange = async (values) => {
    try {
      const token = localStorage.getItem("jwtToken");
      const response = await axios.put(
          `http://localhost:8080/api/v1/user/changepassword/${userData.id}/${values.password}`,
          {},
          {
            headers: {
              Authorization: `Bearer ${token}`,
              "Content-Type": "application/json",
            },
          }
      );

      if (response.status === 200) {
        message.success("Password changed successfully");
        setIsPasswordModalVisible(false);
      } else {
        message.error("Failed to change password");
      }
    } catch (error) {
      console.error("Failed to change password:", error);
      message.error("Failed to change password. Please try again.");
    }
  };


  return (
      <>
        <div
            className="profile-nav-bg"
            style={{ backgroundImage: `url(${BgProfile})` }}
        ></div>

        <Card
            className="card-profile-head"
            bodyStyle={{ display: "none" }}
            title={
              <Row justify="space-between" align="middle" gutter={[24, 0]}>
                <Col span={24} md={12} className="col-info">
                  <Avatar.Group>
                    <Avatar size={74} shape="square" src={userData?.pictureUrl || profilavatar} />                    <div className="avatar-info">
                      <h4 className="font-semibold m-0">
                        {userData?.firstName} {userData?.lastName}
                      </h4>
                      <p>{userData?.role}</p>
                    </div>
                  </Avatar.Group>
                </Col>
                <Col
                    span={24}
                    md={12}
                    style={{
                      display: "flex",
                      alignItems: "center",
                      justifyContent: "flex-end",
                    }}
                >
                  <Radio.Group defaultValue="a" onChange={(e) => {
                    if (e.target.value === "d") {
                      handlePasswordClick();
                    }
                  }}>
                    <Radio.Button value="a">OVERVIEW</Radio.Button>
                    <Radio.Button value="b">TEAMS</Radio.Button>
                    <Radio.Button value="c">PROJECTS</Radio.Button>
                    <Radio.Button value="d">CHANGE PASSWORD</Radio.Button>
                  </Radio.Group>
                </Col>
              </Row>
            }
        />



        <Row gutter={[24, 0]}>
          <Col span={24} md={8} className="mb-24 ">
            <Card
                bordered={false}
                className="header-solid h-full"
                title={<h6 className="font-semibold m-0">Platform Settings</h6>}
            >
              <ul className="list settings-list">
                <li>
                  <h6 className="list-header text-sm text-muted">ACCOUNT</h6>
                </li>
                <li>
                  <Switch defaultChecked />
                  <span>Email me when someone follows me</span>
                </li>
                <li>
                  <Switch />
                  <span>Email me when someone answers me</span>
                </li>
                <li>
                  <Switch defaultChecked />
                  <span>Email me when someone mentions me</span>
                </li>
                <li>
                  <h6 className="list-header text-sm text-muted m-0">
                    APPLICATION
                  </h6>
                </li>
                <li>
                  <Switch defaultChecked />
                  <span>New launches and projects</span>
                </li>
                <li>
                  <Switch defaultChecked />
                  <span>Monthly product updates</span>
                </li>
                <li>
                  <Switch defaultChecked />
                  <span>Subscribe to newsletter</span>
                </li>
              </ul>
            </Card>
          </Col>
          <Col span={24} md={8} className="mb-24">
            <Card
                bordered={false}
                title={<h6 className="font-semibold m-0">Profile Information</h6>}
                className="header-solid h-full card-profile-information"
                extra={
                  <Button type="link" onClick={handlePencilClick}>
                    {pencil}
                  </Button>
                }
                bodyStyle={{ paddingTop: 0, paddingBottom: 16 }}
            >
              <Descriptions title="">
                <Descriptions.Item label="Full Name" span={3}>
                  {userData?.firstName} {userData?.lastName}
                </Descriptions.Item>
                <Descriptions.Item label="Mobile" span={3}>
                  {userData?.phoneNumber}
                </Descriptions.Item>
                <Descriptions.Item label="Email" span={3}>
                  {userData?.email}
                </Descriptions.Item>
                <Descriptions.Item label="Role" span={3}>
                  {userData?.role}
                </Descriptions.Item>
              </Descriptions>
              <hr className="my-25" />
              <h6 className="font-semibold text-muted text-sm">Social Media</h6>
              <div className="social">
                <Button
                    type="link"
                    className="px-0 btn-social"
                    icon={<FacebookOutlined />}
                >
                  <span>Facebook</span>
                </Button>
                <Button
                    type="link"
                    className="px-0 btn-social"
                    icon={<TwitterOutlined />}
                >
                  <span>Twitter</span>
                </Button>
                <Button
                    type="link"
                    className="px-0 btn-social"
                    icon={<InstagramOutlined />}
                >
                  <span>Instagram</span>
                </Button>
              </div>
            </Card>
          </Col>
          <Col span={24} md={8} className="mb-24">
            <Card
                bordered={false}
                className="header-solid h-full"
                title={<h6 className="font-semibold m-0">Conversations</h6>}
            >
              <List
                  itemLayout="horizontal"
                  dataSource={data}
                  split={false}
                  className="conversations-list"
                  renderItem={(item) => (
                      <List.Item>
                        <List.Item.Meta
                            avatar={<Avatar shape="square" size={48} src={item.avatar} />}
                            title={item.title}
                            description={item.description}
                        />
                      </List.Item>
                  )}
              />
            </Card>
          </Col>
        </Row>

        <Row gutter={[24, 0]}>
          <Col span={24} md={12} className="mb-24">
            <Card
                bordered={false}
                className="header-solid h-full"
                title={<h6 className="font-semibold m-0">Projects</h6>}
                extra={
                  <Button type="primary">
                    <Upload
                        name="avatar"
                        listType="picture-card"
                        className="avatar-uploader"
                        showUploadList={false}
                        action="https://www.mocky.io/v2/5cc8019d300000980a055e76"
                        beforeUpload={beforeUpload}
                        onChange={handleChange}
                    >
                      {uploadButton}
                    </Upload>
                  </Button>
                }
            >
              <Row gutter={[24, 0]}>
                {project.map((value, index) => (
                    <Col span={24} md={12} key={index}>
                      <Card
                          className="card-project"
                          cover={<img alt={value.titlesub} src={value.img} />}
                      >
                        <h6>{value.titlesub}</h6>
                        <h4>{value.title}</h4>
                        <p>{value.disciption}</p>
                      </Card>
                    </Col>
                ))}
              </Row>
            </Card>
          </Col>
        </Row>
        <Modal
            title="Change Password"
            visible={isPasswordModalVisible}
            onCancel={handlePasswordCancel}
            footer={null}
        >
          <Form onFinish={handlePasswordChange}>
            <Form.Item
                name="password"
                label="New Password"
                rules={[{ required: true, message: "Please input your new password!" }]}
            >
              <Input.Password />
            </Form.Item>
            <Form.Item>
              <Button type="primary" htmlType="submit">
                Change Password
              </Button>
            </Form.Item>
          </Form>
        </Modal>


        <Modal
            title="Update Profile"
            visible={isModalVisible}
            onCancel={handleCancel}
            footer={null}
        >
          <Form
              layout="vertical"
              initialValues={{
                ...userData,
                dateOfBirth: userData ? moment(userData.dateOfBirth) : null,
              }}
              onFinish={handleFormSubmit}
          >
            <Form.Item
                name="firstName"
                label="First Name"
                rules={[{ required: true, message: "Please input your first name!" }]}
            >
              <Input />
            </Form.Item>
            <Form.Item
                name="lastName"
                label="Last Name"
                rules={[{ required: true, message: "Please input your last name!" }]}
            >
              <Input />
            </Form.Item>
            <Form.Item
                name="email"
                label="Email"
                rules={[{ required: true, message: "Please input your email!" }]}
            >
              <Input />
            </Form.Item>
            <Form.Item
                name="phoneNumber"
                label="Phone Number"
                rules={[{ required: true, message: "Please input your phone number!" }]}
            >
              <Input />
            </Form.Item>
            <Form.Item
                name="dateOfBirth"
                label="Date of Birth"
                rules={[{ required: true, message: "Please input your date of birth!" }]}
            >
              <DatePicker format="YYYY-MM-DD" />
            </Form.Item>

            {/* Upload Picture */}
            <Form.Item
                name="img"
                label="Profile Picture"
                valuePropName="fileList"
                getValueFromEvent={(e) => (Array.isArray(e) ? e : e && e.fileList)}
            >
              <Upload
                  name="img"
                  listType="picture"
                  beforeUpload={beforeUpload}
                  onChange={handleChange}
              >
                <Button icon={<VerticalAlignTopOutlined />}>Upload</Button>
              </Upload>
            </Form.Item>

            <Form.Item>
              <Button type="primary" htmlType="submit">
                Update
              </Button>
            </Form.Item>
          </Form>

        </Modal>
      </>

  );
}

export default Profile;