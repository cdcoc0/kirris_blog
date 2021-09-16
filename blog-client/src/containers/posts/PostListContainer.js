import qs from 'qs';
import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { withRouter } from 'react-router-dom';
import PostList from '../../components/posts/PostList';
import { listPosts } from '../../modules/posts';

const PostListContainer = ({match, location}) => {
    const dispatch = useDispatch();
    const {posts, error, loading, user, countPosts} = useSelector(
        ({posts, loading, user}) => ({
            posts: posts.posts,
            error: posts.error,
            loading: loading['posts/LIST_POSTS'],
            user: user.user,
            countPosts: posts.countPosts
        }),
    );

    useEffect(() => {
        const {username} = match.params;
        const {tag, page} = qs.parse(location.search, {
            ignoreQueryPrefix: true,
        });
        dispatch(listPosts({tag, username, page}))
    }, [dispatch, location.search, match.params]);

    return (
        <PostList loading={loading} error={error} posts={posts} showWriteButton={user} countPosts={countPosts} />
    );
};

export default withRouter(PostListContainer);