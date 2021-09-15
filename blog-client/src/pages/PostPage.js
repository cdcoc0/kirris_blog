import React from 'react';
// import Sidebar from '../components/common/Sidebar';
import HeaderContainer from '../containers/common/HeaderContainer';
import PostViewerContainer from '../containers/post/PostViewerContainer';

const PostPage = () => {
    return (
        <>
            <HeaderContainer />
            {/* <Sidebar /> */}
            <PostViewerContainer />
        </>
    );
};

export default PostPage;