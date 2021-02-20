import { AxiosResponse } from 'axios';
import api from '@/api/axios';
import { Profile } from '@/types';

export async function fetchProfile(): Promise<Profile> {
  const { data }: AxiosResponse<Profile> = await api.get('/profile');
  return data;
}
