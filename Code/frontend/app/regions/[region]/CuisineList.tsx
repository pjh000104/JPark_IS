"use client";

import Link from "next/link";

type Props = {
  cuisines: string[];
  region: string;
};

export default function CuisineList({ cuisines }: Props) {
  return (
    <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6">
      {cuisines.map((cuisine) => (
        <Link
          key={cuisine}
          href={`/cuisines/${encodeURIComponent(cuisine)}`}
          className="
            block
            bg-gray-100 
            p-6 
            rounded-lg 
            shadow 
            hover:shadow-md 
            transition 
            border
            hover:border-gray-300
            text-gray-900
          "
        >
          <h2 className="text-lg font-semibold">{cuisine}</h2>
        </Link>
      ))}
    </div>
  );
}