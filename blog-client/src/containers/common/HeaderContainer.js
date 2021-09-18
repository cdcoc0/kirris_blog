import React from 'react';
import { useDispatch, useSelector } from 'react-redux';
import Header from '../../components/common/Header';
import { logoutThunk } from '../../modules/user';

const HeaderContainer = () => {
    const {user} = useSelector(({user}) => ({user: user.user}))
    const dispatch = useDispatch();
    const onLogout = () => {
        dispatch(logoutThunk());
    };

    return (
        <Header user={user} onLogout={onLogout} />
    );
};

export default HeaderContainer;