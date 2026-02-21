import Link from "next/link";
import { getRegions } from "../lib/api"; 

export default async function Page() {
  const regions = await getRegions(); 

  return (
    <div className="min-h-screen bg-gray-50">
      
      {/* Header Section */}
      <div className="bg-white border-b shadow-sm">
        <div className="max-w-5xl mx-auto px-6 py-8">
          <h1 className="text-3xl font-bold text-gray-800">
            Explore Local Cuisines
          </h1>
          <p className="text-gray-500 mt-2">
            Select a region to discover its traditional foods.
          </p>
        </div>
      </div>

      {/* Content Section */}
      <div className="max-w-5xl mx-auto px-6 py-10">
        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6">
          {regions.map((region: string) => (
            <Link
              key={region}
              href={`/regions/${encodeURIComponent(region)}`}
              className="
                bg-white 
                p-6 
                rounded-lg 
                shadow 
                hover:shadow-md 
                transition 
                border
                hover:border-gray-300
              "
            >
              <h2 className="text-lg font-semibold text-gray-800">
                {region}
              </h2>
            </Link>
          ))}
        </div>
      </div>
    </div>
  );
}